package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;

public interface DepartmentService {
    Iterable<Department> findAll();

    Department findById(int id);

    Department save(DepartmentRequest departmentRequest);

    void delete(int id);

    boolean isUnique(String name, int id);

    void update(DepartmentRequest departmentRequest);
}
