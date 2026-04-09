package com.IshaanBansal.LoggingFramework.LogAnalyzer;


import com.IshaanBansal.LoggingFramework.DTO.DataResponseDTO;
import com.IshaanBansal.LoggingFramework.GitHub.GithubService;
import com.IshaanBansal.LoggingFramework.Loggers.LogEvent;
import com.IshaanBansal.LoggingFramework.Loggers.LoggerBusiness;
import com.IshaanBansal.LoggingFramework.Loggers.LoggerService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LogAnalyzerService {

    @Autowired
    private LoggerBusiness loggerBusiness;

    @Autowired
    private GithubService githubService;

    //Maybe make it into ResponseDTO if required
    public Object filterLogs(String serviceName) {
        //Initial mapping for errors and exceptions and will extend it for hidden bugs & stuff
        List<LogEvent> rawLogs = loggerBusiness.getLoggersByServiceName(serviceName);

        List<LogEvent> filterLogs = rawLogs.stream()
                .filter(logEvent -> ("ERROR").equalsIgnoreCase(logEvent.getLevel()) || ("EXCEPTION").equalsIgnoreCase(logEvent.getLevel()))
                .toList();

        //Make a call from here to findMatchingFileList


        return filterLogs;





    }

    public List<DebugFileDTO> fetchRepoFiles(List<LogEvent> logEventList,
                                             String owner,
                                             String repo,
                                             String branch,
                                             String token){

        List<DebugFileDTO> result = new ArrayList<>();

        // Step 1: fetch repo tree (ALL file paths)
        List<String> repoFiles = githubService.fetchRepoTree(owner, repo, branch, token);

        // Step 2: process each log
        for(LogEvent logEvent : logEventList){

            Map<String,String> contextMap = contextMapping(logEvent);

            // Step 3: find matching files (by filename)
            List<String> matchingFiles = findMatchingFileList(contextMap, repoFiles);

            if(matchingFiles.isEmpty()) continue;

            // 🔹 Step 4: score candidates (cheap - path based)
            List<FileCandidate> candidates = scoreCandidates(matchingFiles, contextMap);

            // 🔹 Step 5: pick top 2 candidates
            List<FileCandidate> topCandidates = candidates.stream()
                    .sorted((a,b) -> b.getScore() - a.getScore())
                    .limit(2)
                    .toList();

            // 🔹 Step 6: fetch content + refine score
            for(FileCandidate candidate : topCandidates){

                String content = githubService.fetchFileContent(
                        owner,
                        repo,
                        candidate.getFilePath(),
                        token
                );

                int extraScore = 0;

                String method = contextMap.get("method");
                String error = contextMap.get("error");

                if(method != null && content.contains(method)){
                    extraScore += 30;
                }

                if(error != null && content.contains(error)){
                    extraScore += 40;
                }

                candidate.setScore(candidate.getScore() + extraScore);

                candidate.setFileContent(content); // store content inside candidate
            }

            // 🔹 Step 7: pick BEST candidate
            FileCandidate best = topCandidates.stream()
                    .max(Comparator.comparingInt(FileCandidate::getScore))
                    .orElse(null);

            if(best != null){
                result.add(new DebugFileDTO(logEvent, best.getFilePath(), best.getFileContent()));
            }
        }

        return result;
    }

    public List<FileCandidate> scoreCandidates(List<String> matchingFiles, Map<String,String> context){

        List<FileCandidate> candidates = new ArrayList<>();

        String fileName = context.get("file");

        for(String path : matchingFiles){

            int score = 0;

            if(fileName != null && path.endsWith(fileName)){
                score += 50;
            }

            if(path.contains("/service/")){
                score += 10;
            }

            if(path.contains("/controller/")){
                score += 5;
            }

            candidates.add(new FileCandidate(path, score));
        }

        return candidates;
    }

    public List<String> findMatchingFileList(Map<String,String> context, List<String> repoFiles){

        List<String> matches = new ArrayList<>();

        String fileName = context.get("file");

        if(fileName == null) return matches;

        for(String filePath : repoFiles){
            if(filePath.endsWith(fileName)){
                matches.add(filePath);
            }
        }

        return matches;
    }

    //Make scoring for getting a good Debug Context and the most appropriate for giving context to AI
    public Map<String,String> contextMapping(LogEvent logEvent){
        String fileName = null;
        String  methodName = null;
        String error = null;
        if(logEvent.getMetadata()!=null){
            Map<String,Object> metadata = logEvent.getMetadata();

            if(metadata.containsKey("file"))fileName = metadata.get("file").toString();
            if(metadata.containsKey("method"))methodName = metadata.get("method").toString();
            if(metadata.containsKey("error"))error = metadata.get("error").toString();

        }
        Map<String,String> contextMap = new HashMap<>();
        contextMap.put("file",fileName);
        contextMap.put("method",methodName);
        contextMap.put("error",error);
        if(logEvent.getMessage()!=null)contextMap.put("logMessage",logEvent.getMessage());
        return contextMap;
    }


}
