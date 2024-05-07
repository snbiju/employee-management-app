package com.employeemanagement.entity;




import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;


@Entity
@Table(name = "EMPLOYEE")
public class Employee implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EMPLOYEE_ID")
    private Long id;

    @Column(name = "EMPLOYEE_NAME")
    private String employeeName;


    @Column(name = "SALARY")
    private double salary;
    @JoinColumn(name = "DEPARTMENT_ID", referencedColumnName = "DEPARTMENT_ID")
    @ManyToOne
    private Department department;

    public Employee() {
    }

    public Employee(Long id, String employeeName, double salary, Department department) {
        this.id = id;
        this.employeeName = employeeName;
        this.salary = salary;
        this.department = department;
    }

    public Long getId() {
        return id;
    }
    public String getEmployeeName() {
        return employeeName;
    }

    public double getSalary() {
        return salary;
    }

    public Department getDepartment() {
        return department;
    }

}



