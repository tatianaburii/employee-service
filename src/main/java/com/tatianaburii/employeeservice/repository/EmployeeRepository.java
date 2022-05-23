package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.domain.Employee;

public interface EmployeeRepository {
    void save(Employee employee);

    int findIdByEmail(String email);
}
