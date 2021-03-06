package com.tatianaburii.employeeservice.controller.dto;

import lombok.*;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentRequest {
    private int id;
    @Size(min=3, max=255, message = "Name must be between 3 and 255 characters")
    String name;
}
