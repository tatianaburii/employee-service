package com.tatianaburii.employeeservice.domain;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;

public class DepartmentRequestFixture {
    public static DepartmentRequest createDepartmentRequest(){
        return DepartmentRequest.builder()
                .id(2)
                .name("department")
                .build();
    }
}