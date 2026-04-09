package com.IshaanBansal.LoggingFramework.LogAnalyzer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebugContext {
    private String serviceName;
    private String errorMessage;
    private String probableFile;
}
