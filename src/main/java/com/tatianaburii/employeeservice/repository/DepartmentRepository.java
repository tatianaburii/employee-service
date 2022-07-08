package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.domain.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Integer> {
    Department findOneByName(String name);

}
