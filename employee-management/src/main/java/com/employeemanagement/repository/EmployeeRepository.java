package com.employeemanagement.repository;

import com.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentDepartmentName(String departmentName);
    List<Employee> findBySalaryGreaterThan(double salary);

    List<Employee> findEmployeesBySalaryLessThan(double salary);
}
