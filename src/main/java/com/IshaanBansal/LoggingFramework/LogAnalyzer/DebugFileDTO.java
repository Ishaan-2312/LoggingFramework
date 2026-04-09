package com.IshaanBansal.LoggingFramework.LogAnalyzer;


import com.IshaanBansal.LoggingFramework.Loggers.LogEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DebugFileDTO {

    private LogEvent logEvent;
    private String filePath;
    private String fileContent;

}
