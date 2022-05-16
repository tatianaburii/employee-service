package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;

public interface DepartmentService {
    void save(DepartmentRequest departmentRequest);
    boolean isUnique(String name);
}
