package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.api.dto.EmployeeDto;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    List<Employee> findAll();

    Optional<Employee> findById(Long id);

    Employee create(EmployeeDto employeeDto, Department department);

    void delete(Employee employee);

    boolean isUnique(String email);

    void update(Employee employee, EmployeeDto employeeDto, Department department);

}
