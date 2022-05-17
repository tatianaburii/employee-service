package com.tatianaburii.employeeservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor

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
