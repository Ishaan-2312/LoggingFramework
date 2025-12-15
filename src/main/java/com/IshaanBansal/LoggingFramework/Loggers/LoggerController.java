package com.IshaanBansal.LoggingFramework.Loggers;


import com.IshaanBansal.LoggingFramework.DTO.DataResponseDTO;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping()
public class LoggerController {

    @Autowired
    private LoggerService loggerService;

    @PostMapping("/postLoggers")
    public ResponseEntity<DataResponseDTO> postLoggers(@RequestBody LogBatchRequest loggers){
        return ResponseEntity.ok(loggerService.postLoggers(loggers));
    }


    @GetMapping("/getLoggers")
    public ResponseEntity<DataResponseDTO> getLoggersByServiceName(@RequestParam("serviceName") String serviceName){
        return ResponseEntity.ok(loggerService.getLoggersByServiceName(serviceName));
    }

    @GetMapping("/getTimedLoggers")
    public ResponseEntity<DataResponseDTO> getLoggersByServiceNameAndTime(@RequestParam("serviceName") String serviceName,@RequestParam("time") LocalDateTime time){
        return ResponseEntity.ok(loggerService.getLoggersByServiceNameAndTime(serviceName,time));
    }
}
