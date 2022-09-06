package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.DepartmentDto;
import com.tatianaburii.employeeservice.domain.Department;

public interface DepartmentService {
    Iterable<Department> findAll();

    Department findById(int id);

    Department save(DepartmentDto departmentDto);

    void delete(int id);

    boolean isUnique(String name, int id);

    void update(DepartmentDto departmentDto);
}
