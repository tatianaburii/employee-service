package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

  List<Employee> findByActiveTrueAndDepartmentActiveTrue();

  Optional<Employee> findByIdAndActiveTrue(Long id);

  boolean existsByEmailAndActiveTrue(String email);

}
