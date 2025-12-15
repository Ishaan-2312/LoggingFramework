package com.IshaanBansal.LoggingFramework.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DataResponseDTO<T> {
    private T data;
    private String message;
    private Integer statusCode;
    private Error error;
}
