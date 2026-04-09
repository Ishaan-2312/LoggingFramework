package com.IshaanBansal.LoggingFramework.LogAnalyzer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileCandidate {

    private String filePath;
    private int score;
    private String fileContent;

    public FileCandidate(String filePath, int score) {
        this.filePath = filePath;
        this.score = score;
    }


}