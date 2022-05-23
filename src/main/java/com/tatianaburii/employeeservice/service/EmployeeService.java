package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;

public interface EmployeeService {
    void save(EmployeeRequest employeeRequest);

    boolean isUnique(String email);
}
