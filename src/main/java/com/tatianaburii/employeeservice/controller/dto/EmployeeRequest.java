package com.tatianaburii.employeeservice.controller.dto;

import com.tatianaburii.employeeservice.domain.Department;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRequest {
    private int id;
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    private String name;
    @Size(min = 9, max = 30, message = "Phone number must be between 9 and 30 characters")
    private String phone;
    @Email
    @Size(min = 10, max = 30, message = "Email must be between 10 and 30 characters")
    private String email;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    @NotNull
    private Department department;
}

