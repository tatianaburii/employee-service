package com.tatianaburii.employeeservice.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class EmployeeRequest extends AbstractRequest {

    @Size(min = 9, max = 30, message = "Phone number must be between 9 and 30 characters")
    private String phone;
    @Email
    @Size(min = 10, max = 30, message = "Email must be between 10 and 30 characters")
    private String email;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    private int departmentId;

    public EmployeeRequest(int id, String name, String phone, String email, LocalDate dateOfBirth, int departmentId) {
        super(id, name);
        this.phone = phone;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.departmentId = departmentId;
    }

    public EmployeeRequest() {
    }

    @Override
    public String toString() {
        return "EmployeeRequest{" +
                " id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", departmentId=" + departmentId +
                '}';
    }
}
