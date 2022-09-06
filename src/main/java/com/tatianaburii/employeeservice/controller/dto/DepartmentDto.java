package com.tatianaburii.employeeservice.controller.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = PRIVATE)
public class DepartmentDto {
    int id;
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    String name;
}
