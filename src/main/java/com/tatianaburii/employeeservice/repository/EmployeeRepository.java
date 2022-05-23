package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.domain.Employee;

import java.util.List;

public interface EmployeeRepository {
    void save(Employee employee);

    int findIdByEmail(String email);

    List<Employee> findAll();

    void delete(int id);
}
