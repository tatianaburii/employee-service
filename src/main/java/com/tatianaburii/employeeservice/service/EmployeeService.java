package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.Employee;

import java.util.List;

public interface EmployeeService {
    void save(EmployeeRequest employeeRequest);

    boolean isUnique(String email);

    List<Employee> findAll();
}
