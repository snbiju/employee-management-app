package com.employeemanagement.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class BulkInsertRequestTest {

    @Test
    void testBulkInsertRequest() {
        // Given
        List<DepartmentDTO> departments = new ArrayList<>();
        List<EmployeeDTO> employees = new ArrayList<>();

        // Initialize some test data
        DepartmentDTO department1 = new DepartmentDTO();
        department1.setName("Engineering");
        departments.add(department1);

        EmployeeDTO employee1 = new EmployeeDTO();
        employee1.setEmployeeName("Alice");
        employee1.setDepartment(department1);
        employees.add(employee1);

        // When
        BulkInsertRequest bulkInsertRequest = new BulkInsertRequest(departments, employees);

        // Then
        assertNotNull(bulkInsertRequest); // Verify the record is not null
        assertEquals(departments, bulkInsertRequest.departments()); // Verify departments list
        assertEquals(employees, bulkInsertRequest.employees()); // Verify employees list
    }
}
