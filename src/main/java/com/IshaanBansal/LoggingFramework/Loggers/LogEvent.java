package com.IshaanBansal.LoggingFramework.Loggers;

import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class LogEvent {

    private String id;
    private String serviceName;
    private String level;
    private String message;
    private LocalDateTime timestamp;
    private Map<Object,Object> metadata;


}
