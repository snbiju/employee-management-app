package com.employeemanagement.exception;


import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        String message,
        List<String> details
) {
}

