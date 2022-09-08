package com.tatianaburii.employeeservice.service;

import com.tatianaburii.employeeservice.controller.dto.DepartmentDto;
import com.tatianaburii.employeeservice.domain.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    List<Department> findAll();

    Optional<Department> findById(Long id);

    Department create(DepartmentDto departmentDto);

    void delete(Department department);

    boolean isUnique(String name);

    void update(Department department, DepartmentDto departmentDto);
}
