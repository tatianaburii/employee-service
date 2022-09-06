package com.tatianaburii.employeeservice.domain;

import com.tatianaburii.employeeservice.controller.dto.EmployeeDto;

import java.time.LocalDate;

public class EmployeeRequestFixture {
    public static EmployeeDto createEmployeeRequest(){
        return EmployeeDto.builder()
                .id(1)
                .name("Employee")
                .email("employee@gmail.com")
                .phone("0993882441")
                .dateOfBirth(LocalDate.of(1997,1,15))
                .build();
    }
}