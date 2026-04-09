package com.IshaanBansal.LoggingFramework.LogAnalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogAnalyzerController {

    @Autowired
    private LogAnalyzerService logAnalyzerService;

    public ResponseEntity<?> filterLogs(@RequestParam("serviceName") String serviceName){
        return ResponseEntity.ok(logAnalyzerService.filterLogs(serviceName));
    }
}
