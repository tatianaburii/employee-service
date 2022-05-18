package com.tatianaburii.employeeservice.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    private int id;
    private String name;
    private LocalDateTime createdAt;
    private boolean active;

    public Department(String name) {
        this.name = name;
        this.createdAt = LocalDateTime.now();
        this.active = true;
    }
}
