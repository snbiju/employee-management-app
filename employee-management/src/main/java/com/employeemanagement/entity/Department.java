package com.employeemanagement.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
@Entity
@Table(name = "DEPARTMENT")
public class Department implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENT_ID")
    private Long id;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName;
    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private Collection<Employee> employeeCollection;

    public Department() {
    }

    public Department(Long id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }

    public Long getId() {
        return id;
    }



    public String getName() {
        return departmentName;
    }



}