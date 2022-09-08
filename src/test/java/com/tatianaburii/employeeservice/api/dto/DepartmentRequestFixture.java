package com.tatianaburii.employeeservice.api.dto;

import com.tatianaburii.employeeservice.controller.dto.DepartmentDto;

public class DepartmentRequestFixture {
  public static DepartmentDto createDepartmentRequest() {
    return DepartmentDto.builder()
        .id(1L)
        .name("department")
        .build();
  }
}