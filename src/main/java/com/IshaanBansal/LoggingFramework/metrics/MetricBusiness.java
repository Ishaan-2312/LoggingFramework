package com.IshaanBansal.LoggingFramework.metrics;

import com.IshaanBansal.LoggingFramework.DTO.DataResponseDTO;
import com.IshaanBansal.LoggingFramework.Loggers.LogBatchRequest;
import com.IshaanBansal.LoggingFramework.Loggers.LogEvent;
import com.IshaanBansal.LoggingFramework.Loggers.LoggerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class MetricBusiness {

    @Autowired
    private MetricDAO metricDAO;

    @Autowired
    private LoggerDAO loggerDAO;

    private static Logger LOGGER = LoggerFactory.getLogger(MetricBusiness.class);


    //Test krna h kal bas ek baar
    public DataResponseDTO getThroughputMetrics(String serviceName, LocalDateTime startTime) {
         try {
             LocalDateTime endTime = startTime.plusSeconds(10);

//             ServiceMetricsDO serviceMetricsDO= new ServiceMetricsDO();
             ServiceMetricsDO oldServiceMetricsDO = metricDAO.getThroughputMetrics(serviceName,startTime);

             if (oldServiceMetricsDO==null && serviceName != null) {
                 List<LogBatchRequest> logBatchRequestList = loggerDAO.getLogEventsByServiceNameAndTime(serviceName, startTime);
                 List<LogEvent> logEventList = new ArrayList<>();
                 if (logBatchRequestList != null && !logBatchRequestList.isEmpty()) {
                     for (LogBatchRequest logBatchRequest : logBatchRequestList) {
                         if (logBatchRequest.getLogEvents() != null && !logBatchRequest.getLogEvents().isEmpty()) {
                             for (LogEvent logEvent : logBatchRequest.getLogEvents()) {
                                 logEventList.add(logEvent);
                             }
                         }
                     }
                 }
                 int loggedEventsSize = logEventList.size();

                 if (loggedEventsSize != 0) {
                     ServiceMetricsDO serviceMetricsDO = new ServiceMetricsDO();
                     serviceMetricsDO.setServiceName(serviceName);
                     serviceMetricsDO.setTotalLogs(loggedEventsSize);
                     serviceMetricsDO.setWindowStart(startTime);
                     serviceMetricsDO.setWindowEnd(endTime);
                     metricDAO.saveOrUpdateThroughputMetrics(serviceMetricsDO);
                     return DataResponseDTO.builder().message("Fetched Result Succesfully").data(loggedEventsSize / 10).statusCode(200).build();

                 }
             }
             return DataResponseDTO.builder().data(oldServiceMetricsDO).statusCode(200).build();


             }catch (Exception e){
             LOGGER.error("Exception in Throughput Metrics :{}",serviceName,e);
         }
        return DataResponseDTO.builder().statusCode(404).message("No Logged Events found for this Service in the given time").build();

    }
}
