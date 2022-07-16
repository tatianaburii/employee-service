package com.tatianaburii.employeeservice.domain;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;

import java.time.LocalDate;

public class EmployeeRequestFixture {
    public static EmployeeRequest createEmployeeRequest(){
        return EmployeeRequest.builder()
                .id(1)
                .name("Employee")
                .email("employee@gmail.com")
                .phone("0993882441")
                .dateOfBirth(LocalDate.of(1997,1,15))
                .build();
    }
}