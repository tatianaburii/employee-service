package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;

import java.util.List;

public interface DepartmentRepository {
    void save(Department department);
    int findIdByName(String name);
    List<Department> findAll();
    void delete(int id);
    void update(DepartmentRequest departmentRequest);
    Department findById(int id);
}
