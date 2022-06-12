package com.tatianaburii.employeeservice.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString(callSuper = true)
public class Department extends AbstractEntity {

    public Department(String name) {
        super(name);
    }

    public Department() {
    }

    public Department(int id, String name, LocalDateTime createdAt, boolean active) {
        super(id, name, createdAt, active);
    }
}
