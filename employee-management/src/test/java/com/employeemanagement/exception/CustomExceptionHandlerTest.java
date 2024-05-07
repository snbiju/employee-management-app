package com.employeemanagement.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.ServletWebRequest;

class CustomExceptionHandlerTest {

    private CustomExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        exceptionHandler = new CustomExceptionHandler();
    }

    @Test
    void testHandleDepartmentNotFoundException() {
        // Given
        DepartmentNotFoundException exception = new DepartmentNotFoundException("Department not found");
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setPreferredLocales(List.of(Locale.ENGLISH));
        WebRequest webRequest = new ServletWebRequest(servletRequest);

        // When
        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleDepartmentNotFoundException(exception, webRequest);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        ErrorResponse errorResponse = responseEntity.getBody();
        assertEquals(LocalDateTime.now().getDayOfMonth(), errorResponse.timestamp().getDayOfMonth()); // Check timestamp (ignoring smaller time units)
        assertEquals("Employee or Department does not exist!", errorResponse.message());
        List<String> errorDetails = errorResponse.details();
        assertEquals("English", errorDetails.get(0)); // Locale display name
        assertEquals("Department not found", errorDetails.get(1)); // Exception message
    }
}
