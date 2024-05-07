package com.employeemanagement.util;

import com.employeemanagement.dto.DepartmentDTO;
import com.employeemanagement.dto.EmployeeDTO;
import com.employeemanagement.entity.Department;
import com.employeemanagement.entity.Employee;

import java.util.List;

public class EntityDtoTranslator {

    // Private constructor to prevent instantiation
    private EntityDtoTranslator() {
    }


    /**
     * Converts a list of EmployeeDTOs to Employee entities.
     *
     * @param employeeDTOs list of EmployeeDTOs
     * @return list of Employee entities
     */
    public static List<Employee> toEmployees(List<EmployeeDTO> employeeDTOs) {
        return employeeDTOs.stream()
                .map(EntityDtoTranslator::toEmployee)
                .toList();
    }

    /**
     * Converts a list of EmployeeDTOs to Employee entities.
     *
     * @param employees list of Employee entities
     * @return list of Employee DTO
     */
    public static List<EmployeeDTO> toEmployeesDTO(List<Employee> employees) {
        return employees.stream()
                .map(EntityDtoTranslator::toEmployeeDTO)
                .toList();
    }

    /**
     * Converts a single EmployeeDTO to an Employee entity.
     *
     * @param employeeDTO the EmployeeDTO
     * @return the Employee entity
     */
    private static Employee toEmployee(EmployeeDTO employeeDTO) {
        return new Employee(employeeDTO.getId(), employeeDTO.getEmployeeName(), employeeDTO.getSalary(), toDepartment(employeeDTO.getDepartment()));
    }

    /**
     * Converts a single EmployeeDTO to an Employee entity.
     *
     * @param employee the Employee entity
     * @return the Employee DTO
     */
    private static EmployeeDTO toEmployeeDTO(Employee employee) {
        return new EmployeeDTO(employee.getId(), employee.getEmployeeName(), employee.getSalary(), toDepartmentDTO(employee.getDepartment()));
    }

    /**
     * Converts a list of DepartmentDTOs to Department entities.
     *
     * @param departmentDTOs list of DepartmentDTOs
     * @return list of Department entities
     */
    public static List<Department> toDepartments(List<DepartmentDTO> departmentDTOs) {
        return departmentDTOs.stream()
                .map(EntityDtoTranslator::toDepartment).toList();

    }

    /**
     * Converts a list of Department to Department DTOs.
     *
     * @param departments list of DepartmentDTOs
     * @return list of Department entities
     */
    public static List<DepartmentDTO> toDepartmentsDTOs(List<Department> departments) {
        return departments.stream()
                .map(EntityDtoTranslator::toDepartmentDTO).toList();

    }

    /**
     * Converts a single DepartmentDTO to a Department entity.
     *
     * @param departmentDTO the DepartmentDTO
     * @return the Department entity
     */
    private static Department toDepartment(DepartmentDTO departmentDTO) {
        return new Department(departmentDTO.getId(), departmentDTO.getName());
    }


    /**
     * Converts a single Department to a DepartmentDTO.
     *
     * @param department the Department entity
     * @return the Department DTO
     */
    private static DepartmentDTO toDepartmentDTO(Department department) {
        return new DepartmentDTO(department.getId(), department.getName());
    }
}
