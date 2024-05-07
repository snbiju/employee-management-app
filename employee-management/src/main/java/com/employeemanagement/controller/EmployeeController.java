package com.employeemanagement.controller;

import com.employeemanagement.dto.BulkInsertRequest;
import com.employeemanagement.dto.DepartmentDTO;
import com.employeemanagement.dto.EmployeeDTO;
import com.employeemanagement.exception.DepartmentNotFoundException;
import com.employeemanagement.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Fetches employees by department name.
     *
     * @param departmentName the name of the department
     * @return list of employees in the given department
     * @throws DepartmentNotFoundException if no employees are found in the department
     */
    @GetMapping("/department/{departmentName}")
    public List<EmployeeDTO> getEmployeesByDepartment(@PathVariable String departmentName) throws DepartmentNotFoundException {
        logger.debug("Entering getEmployeesByDepartment with departmentName: {}", departmentName);
        List<EmployeeDTO> employees = employeeService.getEmployeesByDepartment(departmentName);
        logger.debug("Exiting getEmployeesByDepartment with result size: {}", employees.size());
        return employees;
    }

    /**
     * Fetches employees based on salary conditions.
     *
     * @param salary    the salary value to filter by
     * @param condition the condition ("greaterThan" or "lessThan") to filter by
     * @return list of employees that meet the condition
     */
    @GetMapping("/salary/{salary}")
    public List<EmployeeDTO> getEmployeesBySalary(@PathVariable double salary, @RequestParam String condition) {
        logger.debug("Entering getEmployeesBySalary with salary: {}, condition: {}", salary, condition);
        List<EmployeeDTO> employees;
        switch (condition) {
            case "greaterThan" -> employees = employeeService.getEmployeesBySalaryGreaterThan(salary);
            case "lessThan" -> employees = employeeService.getEmployeesBySalaryLessThan(salary);
            default -> {
                logger.error("Invalid condition parameter: {}", condition);
                throw new IllegalArgumentException("Invalid condition parameter. Use 'greaterThan' or 'lessThan'.");
            }
        }
        logger.debug("Exiting getEmployeesBySalary with result size: {}", employees.size());
        return employees;
    }

    /**
     * Fetches all employees.
     *
     * @return list of all employees
     */
    @GetMapping
    public List<EmployeeDTO> getAllEmployees() {
        logger.debug("Entering getAllEmployees");
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        logger.debug("Exiting getAllEmployees with result size: {}", employees.size());
        return employees;
    }

    /**
     * Fetches all departments.
     *
     * @return list of all departments
     */
    @GetMapping("/department")
    public List<DepartmentDTO> getAllDepartments() {
        logger.debug("Entering getAllDepartments");
        List<DepartmentDTO> departments = employeeService.getAllDepartment();
        logger.debug("Exiting getAllDepartments with result size: {}", departments.size());
        return departments;
    }

    /**
     * Handles bulk insertion of employees and departments.
     *
     * @param request the request containing the list of employees and departments to insert
     * @return ResponseEntity indicating the status of the operation
     */
    @PostMapping(value = "/bulk-insert", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> bulkInsert(@Valid @RequestBody BulkInsertRequest request) {
        logger.debug("Entering bulkInsert with request: {}", request);
        employeeService.bulkInsert(request);
        logger.debug("Exiting bulkInsert");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
