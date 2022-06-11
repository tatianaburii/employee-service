package com.tatianaburii.employeeservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractRequest {
    int id;
    @Size(min=3, max=255, message = "Name must be between 3 and 255 characters")
    String name;
}