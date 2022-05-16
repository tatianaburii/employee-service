package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.domain.Department;


public interface DepartmentRepository {
    void save(Department department);
    int findIdByName(String name);
}

