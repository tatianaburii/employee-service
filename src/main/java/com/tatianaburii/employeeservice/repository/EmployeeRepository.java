package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.domain.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {
    Employee findOneByEmail(String email);

}
