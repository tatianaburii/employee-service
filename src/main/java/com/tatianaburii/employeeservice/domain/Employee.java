package com.tatianaburii.employeeservice.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class Employee extends AbstractEntity{
    private String phone;
    private String email;
    private int departmentId;
    private LocalDate dateOfBirth;

    public Employee(String name, String phone, String email, LocalDate dateOfBirth, int departmentId) {
        super(name);
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.departmentId = departmentId;
    }

    public Employee(int id, String name, LocalDateTime createdAt, boolean active, String phone, String email, int departmentId, LocalDate dateOfBirth) {
        super(id, name, createdAt, active);
        this.phone = phone;
        this.email = email;
        this.departmentId = departmentId;
        this.dateOfBirth = dateOfBirth;
    }

    public Employee(String name, String phone, String email, int departmentId, LocalDate dateOfBirth) {
        super(name);
        this.phone = phone;
        this.email = email;
        this.departmentId = departmentId;
        this.dateOfBirth = dateOfBirth;
    }

    public Employee() {
    }
}

