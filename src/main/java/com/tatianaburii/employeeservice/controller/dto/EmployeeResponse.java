package com.tatianaburii.employeeservice.controller.dto;

import com.tatianaburii.employeeservice.domain.Employee;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponse {
  Long id;
  String name;
  String email;
  String phone;
  String departmentName;
  LocalDate dateOfBirth;

    public static EmployeeResponse fromEntity(Employee employee) {
      return EmployeeResponse.builder()
          .id(employee.getId())
          .name(employee.getName())
          .phone(employee.getPhone())
          .email(employee.getEmail())
          .dateOfBirth(employee.getDateOfBirth())
          .departmentName(employee.getDepartment().getName())
          .build();
    }
}
