package com.tatianaburii.employeeservice.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private int id;
    private String name;
    private String phone;
    private String email;
    private LocalDateTime createdAt;
    private boolean active;
    private int departmentId;

    public Employee(String name, String phone, String email, int departmentId) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.active = true;
        this.departmentId = departmentId;
    }
}
