package com.employeemanagement.service;

import com.employeemanagement.dto.BulkInsertRequest;
import com.employeemanagement.dto.DepartmentDTO;
import com.employeemanagement.dto.EmployeeDTO;
import com.employeemanagement.entity.Department;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exception.DepartmentNotFoundException;
import com.employeemanagement.repository.DepartmentRepository;
import com.employeemanagement.repository.EmployeeRepository;
import com.employeemanagement.util.EntityDtoTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEmployeesByDepartment() throws DepartmentNotFoundException {
        String departmentName = "Engineering";
        List<Employee> employees = Arrays.asList(new Employee(1L, "Mary",50000.00,new Department(1L,"Engineering")));

        when(employeeRepository.findByDepartmentDepartmentName(departmentName)).thenReturn(employees);

        List<EmployeeDTO> result = employeeService.getEmployeesByDepartment(departmentName);

        assertNotNull(result);
        assertEquals(departmentName,result.get(0).getDepartment().getName());
        assertEquals(employees.size(), result.size());
        verify(employeeRepository, times(1)).findByDepartmentDepartmentName(departmentName);
    }

    @Test
    void testGetEmployeesByDepartmentNotFound() {
        String departmentName = "NonExisting";
        String message ="Employee or Department does not exist!";
        when(employeeRepository.findByDepartmentDepartmentName(departmentName)).thenReturn(Collections.emptyList());

        DepartmentNotFoundException exception = assertThrows(
                DepartmentNotFoundException.class,
                () -> employeeService.getEmployeesByDepartment(departmentName)
        );

        assertEquals(message, exception.getMessage());
        verify(employeeRepository, times(1)).findByDepartmentDepartmentName(departmentName);
    }

    @Test
    void testGetEmployeesBySalaryGreaterThan() {
        double salary = 10000;
        List<Employee> employees = Arrays.asList(new Employee(1L, "Bob",5000.00,new Department(1L,"HR")),
                new Employee(2L, "Mary",50000.00,new Department(2L,"Engineering")));
        when(employeeRepository.findBySalaryGreaterThan(salary)).thenReturn(employees);

        List<EmployeeDTO> result = employeeService.getEmployeesBySalaryGreaterThan(salary);

        assertNotNull(result);
        assertEquals(employees.size(), result.size());
        verify(employeeRepository, times(1)).findBySalaryGreaterThan(salary);
    }

    @Test
    void testGetEmployeesBySalaryLessThan() {
        double salary = 10000;
        List<Employee> employees = Arrays.asList(new Employee(1L, "Bob",5000.00,new Department(1L,"HR")),
                new Employee(2L, "Mary",3000.00,new Department(2L,"Engineering")));

        when(employeeRepository.findEmployeesBySalaryLessThan(salary)).thenReturn(employees);

        List<EmployeeDTO> result = employeeService.getEmployeesBySalaryLessThan(salary);

        assertNotNull(result);
        assertEquals(employees.size(), result.size());
        verify(employeeRepository, times(1)).findEmployeesBySalaryLessThan(salary);
    }

    @Test
    void testGetAllEmployees() {
        List<Employee> employees = Arrays.asList(new Employee(1L, "Bob",5000.00,new Department(1L,"HR")),
                new Employee(2L, "Mary",50000.00,new Department(2L,"Engineering")));

        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeDTO> result = employeeService.getAllEmployees();

        assertNotNull(result);
        assertEquals(employees.size(), result.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetAllDepartments() {
        List<Department> departments = Arrays.asList(new Department(), new Department());
        when(departmentRepository.findAll()).thenReturn(departments);

        List<DepartmentDTO> result = employeeService.getAllDepartment();

        assertNotNull(result);
        assertEquals(departments.size(), result.size());
        verify(departmentRepository, times(1)).findAll();

        // Second call: should use cached result, so repository should not be called again
        List<DepartmentDTO> secondCallResult = employeeService.getAllDepartment();
        assertEquals(result, secondCallResult);
    }

    @Test
    void testBulkInsert() {
        BulkInsertRequest request = mock(BulkInsertRequest.class);
        List<Employee> employees = Arrays.asList(new Employee(), new Employee());
        List<Department> departments = Arrays.asList(new Department(), new Department());

        when(request.employees()).thenReturn(Collections.emptyList());
        when(request.departments()).thenReturn(Collections.emptyList());

        employeeService.bulkInsert(request);

        verify(employeeRepository, times(1)).saveAll(anyList());
        verify(departmentRepository, times(1)).saveAll(anyList());
    }
}
