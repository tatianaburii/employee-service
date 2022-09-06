package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.EmployeeDto;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.Employee;

public interface EmployeeService {

    Iterable<Employee> findAll();

    Employee findById(int id);

    Employee save(EmployeeDto employeeDto, Department department);

    void delete(int id);

    boolean isUnique(String email, int id);

    void update(EmployeeDto employeeDto, Department department);

}
