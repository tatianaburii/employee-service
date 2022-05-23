package com.tatianaburii.employeeservice.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
    int id;
    @Size(min = 3, max = 255, message = "Name must be between 3 and 255 characters")
    String name;
    @Size(min = 9, max = 30, message = "Phone number must be between 9 and 30 characters")
    String phone;
    @Email
    @Size(min = 10, max = 30, message = "Email must be between 10 and 30 characters")
    String email;
    int departmentId;
}
