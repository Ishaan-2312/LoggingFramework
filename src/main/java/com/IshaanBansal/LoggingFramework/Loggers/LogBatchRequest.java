package com.IshaanBansal.LoggingFramework.Loggers;

import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "log_batch_request")
@Data
public class LogBatchRequest {

    private String id ;

    private List<LogEvent> logEvents;

    @Override
    public String toString() {
        return "LogBatchRequest{" +
                "id='" + id + '\'' +
                ", logEvents=" + logEvents +
                '}';
    }
}
