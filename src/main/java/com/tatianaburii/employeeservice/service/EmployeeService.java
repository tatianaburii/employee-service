package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Employee;

public interface EmployeeService {

    Iterable<Employee> findAll();

    Employee findById(int id);

    Employee save(EmployeeRequest employeeRequest);

    void delete(int id);

    boolean isUnique(String email, int id);

    void update(EmployeeRequest employeeRequest);

}
