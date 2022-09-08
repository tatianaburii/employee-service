package com.tatianaburii.employeeservice.api.dto;

import com.tatianaburii.employeeservice.controller.dto.EmployeeDto;

import java.time.LocalDate;

public class EmployeeRequestFixture {
  public static EmployeeDto createEmployeeRequest() {
    return EmployeeDto.builder()
        .id(1L)
        .name("Employee")
        .email("employee@gmail.com")
        .phone("0993882441")
        .dateOfBirth(LocalDate.of(1997, 1, 15))
        .departmentId(1L)
        .build();
  }
}