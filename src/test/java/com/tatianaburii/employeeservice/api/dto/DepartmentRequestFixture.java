package com.tatianaburii.employeeservice.api.dto;

public class DepartmentRequestFixture {
  public static DepartmentDto createDepartmentRequest() {
    return DepartmentDto.builder()
        .id(1L)
        .name("department")
        .build();
  }
}