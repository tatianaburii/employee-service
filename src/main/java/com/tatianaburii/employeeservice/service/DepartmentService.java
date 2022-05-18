package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;

import java.util.List;

public interface DepartmentService {
    void save(DepartmentRequest departmentRequest);
    boolean isUnique(String name);
    List<Department> findAll();
}
