package com.employeemanagement.dto;

import java.io.Serializable;
import java.util.List;

public record BulkInsertRequest(
        List<DepartmentDTO> departments,
        List<EmployeeDTO> employees
) implements Serializable {}