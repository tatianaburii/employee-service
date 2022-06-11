package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Employee;

import java.util.List;

public interface EmployeeService extends Service<EmployeeRequest, Employee> {
    List<Employee> findByDepartmentId(int departmentId);
}
