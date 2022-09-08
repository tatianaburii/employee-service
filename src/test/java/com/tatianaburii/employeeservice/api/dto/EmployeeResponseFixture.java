package com.tatianaburii.employeeservice.api.dto;

import com.tatianaburii.employeeservice.controller.dto.EmployeeResponse;

import java.time.LocalDate;

public class EmployeeResponseFixture {
  public static EmployeeResponse createEmployeeResponse(){
    return EmployeeResponse.builder()
        .id(1L)
        .name("Employee")
        .email("employee@gmail.com")
        .phone("0993882441")
        .dateOfBirth(LocalDate.of(1997, 1, 15))
        .departmentName("department")
        .build();
  }
}
