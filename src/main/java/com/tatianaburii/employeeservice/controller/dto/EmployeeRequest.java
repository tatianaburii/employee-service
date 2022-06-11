package com.tatianaburii.employeeservice.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class EmployeeRequest extends AbstractRequest {

    @Size(min = 9, max = 30, message = "Phone number must be between 9 and 30 characters")
    String phone;
    @Email
    @Size(min = 10, max = 30, message = "Email must be between 10 and 30 characters")
    String email;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dateOfBirth;
    int departmentId;

    public EmployeeRequest(int id, String name, String phone, String email, LocalDate dateOfBirth, int departmentId) {
        super(id, name);
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.departmentId = departmentId;
    }
}
