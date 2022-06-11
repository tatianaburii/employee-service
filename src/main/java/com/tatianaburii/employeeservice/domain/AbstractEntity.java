package com.tatianaburii.employeeservice.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public abstract class AbstractEntity {
    protected int id;
    protected String name;
    protected LocalDateTime createdAt;
    protected boolean active;

    protected AbstractEntity(String name) {
        this.name = name;
        this.active = true;
        this.createdAt = LocalDateTime.now();
    }

    public AbstractEntity(int id, String name, LocalDateTime createdAt, boolean active) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.active = active;
    }
}