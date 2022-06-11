package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Employee;

import java.util.List;

public interface EmployeeRepository extends Repository<EmployeeRequest, Employee> {
    List<Employee> findByDepartmentId(int departmentId);
}
