package com.employeemanagement.integrationTest;

import com.employeemanagement.dto.DepartmentDTO;
import com.employeemanagement.dto.EmployeeDTO;
import com.employeemanagement.entity.Department;
import com.employeemanagement.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    void testGetEmployeesByDepartment() throws Exception {
        // Arrange
        List<EmployeeDTO> employees = Arrays.asList(
                new EmployeeDTO(1L,"John Doe", 60000.00, new DepartmentDTO(1L,"Engineering")),
                new EmployeeDTO(2L,"Jane Smith",  55000.00,new DepartmentDTO(2L,"HR"))
        );
        Mockito.when(employeeService.getEmployeesByDepartment("HR")).thenReturn(employees);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/department/HR"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeName").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employeeName").value("Jane Smith"));
    }

    @Test
    void testGetAllDepartment() throws Exception {
        // Arrange
        List<DepartmentDTO> departments = Arrays.asList(
                new DepartmentDTO(6L,"HR"),
                new DepartmentDTO(1L,"Engineering"),
                new DepartmentDTO(5L,"Accounting")
        );

        // Mock the employeeService.getAllDepartment() method to return the list of departments
        Mockito.when(employeeService.getAllDepartment()).thenReturn(departments);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/department"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("HR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Engineering"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value("Accounting"));
    }

    @Test
    void testGetEmployeesBySalary() throws Exception {
        // Arrange
        List<EmployeeDTO> employees = Arrays.asList(
                new EmployeeDTO(1L,"John Doe", 50000.00, new DepartmentDTO(1L,"Engineering")),
                new EmployeeDTO(2L,"Jane Smith",  55000.00,new DepartmentDTO(2L,"HR"))
        );
        Mockito.when(employeeService.getEmployeesBySalaryGreaterThan(50000)).thenReturn(employees);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/salary/50000?condition=greaterThan"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeName").value("John Doe"));
    }

    @Test
    void testGetAllEmployees() throws Exception {
        // Arrange
        List<EmployeeDTO> employees = Arrays.asList(
                new EmployeeDTO(1L,"John Doe", 60000.00, new DepartmentDTO(1L,"Engineering")),
                new EmployeeDTO(2L,"Jane Smith",  55000.00,new DepartmentDTO(2L,"HR"))
        );
        Mockito.when(employeeService.getAllEmployees()).thenReturn(employees);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].employeeName").value("John Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].employeeName").value("Jane Smith"));
    }
}
