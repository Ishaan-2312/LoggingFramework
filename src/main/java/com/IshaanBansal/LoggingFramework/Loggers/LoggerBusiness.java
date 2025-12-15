package com.IshaanBansal.LoggingFramework.Loggers;

import com.IshaanBansal.LoggingFramework.DTO.DataResponseDTO;
import lombok.extern.java.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoggerBusiness {

    @Autowired
    private LoggerDAO loggerDAO;

    private static Logger LOGGER = LoggerFactory.getLogger(LoggerBusiness.class);

    public DataResponseDTO postLoggers(LogBatchRequest logBatchRequest) {
        LOGGER.info("In  postLoggers!!!");

        List<LogEvent> logEventList = new ArrayList<>();
        logEventList = logBatchRequest.getLogEvents();
        if(logEventList!=null && !logEventList.isEmpty()){

            loggerDAO.saveOrUpdateLogBatchRequest(logBatchRequest);
            return DataResponseDTO.builder().statusCode(200).message("LogBatch Request Saved!!!!").build();
        }
        return DataResponseDTO.builder().data("Empty Log Batch Request!!!").statusCode(404).message("Empty Log Batch Request Received from Microservice").build();



    }

    public DataResponseDTO getLoggersByServiceName(String serviceName) {

        List<LogBatchRequest> logEvents = new ArrayList<>();
        LOGGER.info("ServiceName :{}",serviceName);

        logEvents = loggerDAO.getLogEventsByServiceName(serviceName);
        List<LogEvent> logEventList = new ArrayList<>();
        if(logEvents!=null && !logEvents.isEmpty()){
            for(LogBatchRequest logBatchRequest:logEvents){
                if(logBatchRequest!=null && !logBatchRequest.getLogEvents().isEmpty()){
                    for(LogEvent logEvent:logBatchRequest.getLogEvents()){
                        logEventList.add(logEvent);
                    }
                }
            }
        }
        if(logEventList.isEmpty())LOGGER.info("LogEvents List is Empty");
        if(!logEventList.isEmpty()){
            return DataResponseDTO.builder().statusCode(200).message("Received the Log Events for ServiceName :"+ serviceName).data(logEvents).build();

        }

        return DataResponseDTO.builder().statusCode(404).message("Unable to find any LogEvents corresponding to ServiceName "+serviceName).build();


    }

    public DataResponseDTO getLoggersByServiceNameAndTime(String serviceName, LocalDateTime time) {
        List<LogBatchRequest> logEvents = new ArrayList<>();
        LOGGER.info("ServiceName :{}",serviceName);

        logEvents = loggerDAO.getLogEventsByServiceNameAndTime(serviceName,time);
        List<LogEvent> logEventList = new ArrayList<>();
        if(logEvents!=null && !logEvents.isEmpty()){
            for(LogBatchRequest logBatchRequest:logEvents){
                if(logBatchRequest!=null && !logBatchRequest.getLogEvents().isEmpty()){
                    for(LogEvent logEvent:logBatchRequest.getLogEvents()){
                        logEventList.add(logEvent);
                    }
                }
            }
        }
        if(logEventList.isEmpty())LOGGER.info("LogEvents List is Empty");
        if(!logEventList.isEmpty()){
            return DataResponseDTO.builder().statusCode(200).message("Received the Log Events for ServiceName :"+ serviceName).data(logEvents).build();

        }

        return DataResponseDTO.builder().statusCode(404).message("Unable to find any LogEvents corresponding to ServiceName "+serviceName).build();


    }
}
