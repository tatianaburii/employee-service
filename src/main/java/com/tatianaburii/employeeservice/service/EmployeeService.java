package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Employee;

import java.util.List;

public interface EmployeeService {
    void save(EmployeeRequest employeeRequest);

    boolean isUnique(String email, int id);

    List<Employee> findAll();

    void delete(int id);

    void update(EmployeeRequest employeeRequest);

    Employee findById(int id);
}
