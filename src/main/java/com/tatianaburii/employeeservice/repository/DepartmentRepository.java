package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.domain.Department;

import java.util.List;

public interface DepartmentRepository {
    void save(Department department);
    int findIdByName(String name);
    List<Department> findAll();
}
