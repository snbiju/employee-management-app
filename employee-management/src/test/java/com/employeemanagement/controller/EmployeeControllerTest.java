package com.employeemanagement.controller;

import com.employeemanagement.dto.BulkInsertRequest;
import com.employeemanagement.dto.DepartmentDTO;
import com.employeemanagement.dto.EmployeeDTO;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exception.DepartmentNotFoundException;
import com.employeemanagement.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEmployeesByDepartment() throws DepartmentNotFoundException {
        // Given
        String departmentName = "Engineering";
        List<EmployeeDTO> employees = new ArrayList<>();
        EmployeeDTO employee = new EmployeeDTO();
        employee.setEmployeeName("Alice");
        employees.add(employee);

        // Stub the behavior of employeeService
        when(employeeService.getEmployeesByDepartment(departmentName)).thenReturn(employees);

        // When
        List<EmployeeDTO> result = employeeController.getEmployeesByDepartment(departmentName);

        // Then
        assertEquals(employees, result);
        verify(employeeService, times(1)).getEmployeesByDepartment(departmentName);
    }

    @Test
    void testGetEmployeesSalaryGreaterThanGivenAmount() {
        // Given
        double salary = 50000;
        String condition = "greaterThan";
        List<EmployeeDTO> employees = new ArrayList<>();
        when(employeeService.getEmployeesBySalaryGreaterThan(salary)).thenReturn(employees);

        // When
        List<EmployeeDTO> result = employeeController.getEmployeesBySalary(salary, condition);

        // Then
        assertEquals(employees, result);
        verify(employeeService, times(1)).getEmployeesBySalaryGreaterThan(salary);
    }
    @Test
    void testGetEmployeesSalaryLesserThanGivenAmount() {
        // Given
        double salary = 30000;
        String condition = "lessThan";
        List<EmployeeDTO> expectedEmployees = new ArrayList<>();
        expectedEmployees.add(new EmployeeDTO(2L, "Bob",5000.00,new DepartmentDTO(1L,"HR")));

        // Stub the service behavior
        when(employeeService.getEmployeesBySalaryLessThan(salary)).thenReturn(expectedEmployees);

        // When
        List<EmployeeDTO> actualEmployees = employeeController.getEmployeesBySalary(salary, condition);

        // Then
        assertEquals(expectedEmployees, actualEmployees);
        verify(employeeService, times(1)).getEmployeesBySalaryLessThan(salary);
    }
    @Test
    void testGetEmployeesSalaryWithInvalidCondition() {
        // Given
        double salary = 50000;
        String invalidCondition = "invalidCondition";

        // Then
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                employeeController.getEmployeesBySalary(salary, invalidCondition)
        );

        assertEquals("Invalid condition parameter. Use 'greaterThan' or 'lessThan'.", thrown.getMessage());
    }

    @Test
    void testGetAllEmployees() {
        // Given
        List<EmployeeDTO> employees = new ArrayList<>();
        when(employeeService.getAllEmployees()).thenReturn(employees);

        // When
        List<EmployeeDTO> result = employeeController.getAllEmployees();

        // Then
        assertEquals(employees, result);
        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testBulkInsert() {
        // Given
        BulkInsertRequest request = new BulkInsertRequest(new ArrayList<>(), new ArrayList<>());

        // When
        ResponseEntity<Void> response = employeeController.bulkInsert(request);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(employeeService, times(1)).bulkInsert(request);
    }
}
