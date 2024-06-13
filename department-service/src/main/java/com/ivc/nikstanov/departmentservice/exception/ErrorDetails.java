package com.ivc.nikstanov.departmentservice.exception;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String path;
    private String errorCode;
}
