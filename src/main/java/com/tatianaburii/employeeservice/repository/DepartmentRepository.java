package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, Long> {
    List<Department> findByActiveTrue();

    Optional<Department> findByIdAndActiveTrue(Long id);

    boolean existsByNameAndActiveTrue(String name);

}
