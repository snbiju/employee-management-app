package com.employeemanagement.service;

import com.employeemanagement.dto.BulkInsertRequest;
import com.employeemanagement.dto.DepartmentDTO;
import com.employeemanagement.dto.EmployeeDTO;
import com.employeemanagement.entity.Department;
import com.employeemanagement.entity.Employee;
import com.employeemanagement.exception.DepartmentNotFoundException;
import com.employeemanagement.repository.DepartmentRepository;
import com.employeemanagement.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.employeemanagement.util.EntityDtoTranslator.*;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private static final String NOT_FOUND_MESSAGE = "Employee or Department does not exist!";

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Cacheable(value = "employeesCache", key = "#departmentName")
    public List<EmployeeDTO> getEmployeesByDepartment(String departmentName) throws DepartmentNotFoundException {
        logger.debug("Entering getEmployeesByDepartment with departmentName: {}", departmentName);
        List<Employee> employees = employeeRepository.findByDepartmentDepartmentName(departmentName);
        if (employees.isEmpty()) {
            logger.warn("No employees found for department: {}", departmentName);
            throw new DepartmentNotFoundException(NOT_FOUND_MESSAGE);
        }
        logger.debug("Exiting getEmployeesByDepartment with result size: {}", employees.size());
        return toEmployeesDTO(employees);
    }

    @Cacheable(value = "employeesCache", key = "'greaterThan' + #salary")
    public List<EmployeeDTO> getEmployeesBySalaryGreaterThan(double salary) {
        logger.debug("Entering getEmployeesBySalaryGreaterThan with salary: {}", salary);
        List<Employee> employees = employeeRepository.findBySalaryGreaterThan(salary);
        logger.debug("Exiting getEmployeesBySalaryGreaterThan with result size: {}", employees.size());
        return toEmployeesDTO(employees);
    }

    @Cacheable(value = "employeesCache", key = "'lessThan' + #salary")
    public List<EmployeeDTO> getEmployeesBySalaryLessThan(double salary) {
        logger.debug("Entering getEmployeesBySalaryLessThan with salary: {}", salary);
        List<Employee> employees = employeeRepository.findEmployeesBySalaryLessThan(salary);
        logger.debug("Exiting getEmployeesBySalaryLessThan with result size: {}", employees.size());
        return toEmployeesDTO(employees);
    }

    @Cacheable(value = "employeesCache", key = "'all'")
    public List<EmployeeDTO> getAllEmployees() {
        logger.debug("Entering getAllEmployees");
        List<Employee> employees = employeeRepository.findAll();
        logger.debug("Exiting getAllEmployees with result size: {}", employees.size());
        return toEmployeesDTO(employees);
    }

    @Cacheable(value = "departmentsCache", key = "'all'")
    public List<DepartmentDTO> getAllDepartment() {
        logger.debug("Entering getAllDepartment");
        List<Department> departments = departmentRepository.findAll();
        logger.debug("Exiting getAllDepartment with result size: {}", departments.size());
        return toDepartmentsDTOs(departments);
    }

    @Transactional
    @CacheEvict(value = {"employeesCache", "departmentsCache"}, allEntries = true)
    public void bulkInsert(BulkInsertRequest request) {
        logger.debug("Entering bulkInsert with request");
        saveDepartments(toDepartments(request.departments()));
        saveEmployees(toEmployees(request.employees()));
        logger.debug("Exiting bulkInsert");
    }

    private void saveDepartments(List<Department> departments) {
        logger.debug("Saving departments with list size: {}", departments.size());
        departmentRepository.saveAll(departments);
        logger.debug("Departments saved successfully");
    }

    private void saveEmployees(List<Employee> employees) {
        logger.debug("Saving employees with list size: {}", employees.size());
        employeeRepository.saveAll(employees);
        logger.debug("Employees saved successfully");
    }
}
