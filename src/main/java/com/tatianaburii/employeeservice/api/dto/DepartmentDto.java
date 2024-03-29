package com.tatianaburii.employeeservice.api.dto;

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
    Long id;
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    String name;
}
