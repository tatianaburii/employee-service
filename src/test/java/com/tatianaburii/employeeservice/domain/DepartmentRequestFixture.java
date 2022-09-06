package com.tatianaburii.employeeservice.domain;

import com.tatianaburii.employeeservice.controller.dto.DepartmentDto;

public class DepartmentRequestFixture {
    public static DepartmentDto createDepartmentRequest(){
        return DepartmentDto.builder()
                .id(2)
                .name("department")
                .build();
    }
}