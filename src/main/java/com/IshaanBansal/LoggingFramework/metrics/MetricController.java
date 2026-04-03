package com.IshaanBansal.LoggingFramework.metrics;

import com.IshaanBansal.LoggingFramework.DTO.DataResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping
public class MetricController {

    @Autowired
    private MetricService metricService;

    @GetMapping("/getThroughputMetrics")
    public ResponseEntity<DataResponseDTO> getThroughputMetrics(@RequestParam("serviceName") String serviceName, @RequestParam(value = "startTime",required = false)LocalDateTime startTime){
        return ResponseEntity.ok(metricService.getThroughputMetrics(serviceName,startTime));
    }
}
