package com.tatianaburii.employeeservice.domain;

public class DepartmentFixture {
  public static Department createDepartment() {
    return Department.builder()
        .id(1L)
        .name("department")
        .build();
  }
}