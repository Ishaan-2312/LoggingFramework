package com.IshaanBansal.LoggingFramework.Loggers;

import com.IshaanBansal.LoggingFramework.DTO.DataResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoggerService {

    @Autowired
    private LoggerBusiness loggerBusiness;

    public DataResponseDTO postLoggers(LogBatchRequest loggers) {
        return loggerBusiness.postLoggers(loggers);

    }

    public DataResponseDTO getLoggersByServiceName(String serviceName) {
        return loggerBusiness.getLoggersByServiceName(serviceName);
    }

    public DataResponseDTO getLoggersByServiceNameAndTime(String serviceName, LocalDateTime time) {
        return loggerBusiness.getLoggersByServiceNameAndTime(serviceName,time);
    }
}
