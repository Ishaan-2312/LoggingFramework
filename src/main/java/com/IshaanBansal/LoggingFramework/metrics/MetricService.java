package com.IshaanBansal.LoggingFramework.metrics;

import com.IshaanBansal.LoggingFramework.DTO.DataResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MetricService {

    @Autowired
    private MetricBusiness metricBusiness;

    public DataResponseDTO getThroughputMetrics(String serviceName, LocalDateTime startTime) {
        return metricBusiness.getThroughputMetrics(serviceName,startTime);
    }
}
