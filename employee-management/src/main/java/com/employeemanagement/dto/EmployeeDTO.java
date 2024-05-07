package com.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO implements Serializable {

    private Long id;
    private String employeeName;
    private double salary;
    private DepartmentDTO department;

}