package com.tatianaburii.employeeservice.domain;

import java.time.LocalDate;

public class EmployeeFixture {
  public static Employee createEmployee() {
    return Employee.builder()
        .id(1L)
        .name("Employee")
        .email("employee@gmail.com")
        .phone("0993882441")
        .department(DepartmentFixture.createDepartment())
        .dateOfBirth(LocalDate.of(1997, 1, 15))
        .build();
  }
}