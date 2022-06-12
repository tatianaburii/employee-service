package com.tatianaburii.employeeservice.controller.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public abstract class AbstractRequest {
    int id;
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    String name;

    protected AbstractRequest() {
    }

    protected AbstractRequest(int id, String name) {
        this.id = id;
        this.name = name;
    }
}