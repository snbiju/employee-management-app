package com.employeemanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {
    private static final String DEPARTMENT_NOT_FOUND_MESSAGE = "Employee or Department does not exist!";

    /**
     * Handles DepartmentNotFoundException by returning an appropriate error response.
     *
     * @param ex      the exception that was thrown
     * @param request the current web request
     * @return a ResponseEntity containing the error response and HTTP status
     */
    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDepartmentNotFoundException(DepartmentNotFoundException ex, WebRequest request) {
        List<String> errorDetails = new ArrayList<>();
        errorDetails.add(request.getLocale().getDisplayName());
        errorDetails.add(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), DEPARTMENT_NOT_FOUND_MESSAGE, errorDetails);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
