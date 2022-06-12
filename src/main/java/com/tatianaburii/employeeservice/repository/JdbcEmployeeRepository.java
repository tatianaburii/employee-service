package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Employee;
import com.tatianaburii.employeeservice.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JdbcEmployeeRepository implements EmployeeRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcEmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Employee employee) {
        jdbcTemplate.update("INSERT INTO EMPLOYEE (EMPLOYEE_NAME, EMAIL, PHONE, CREATED_AT, DATE_OF_BIRTH, IS_ACTIVE, DEPARTMENT_ID) VALUES(?,?,?,?,?,?,?)",
                employee.getName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getCreatedAt().toString(),
                employee.getDateOfBirth().toString(),
                employee.isActive(),
                employee.getDepartmentId()
        );
    }

    @Override
    public Optional<Integer> findIdByParam(String email) {
       List<Employee> employees = jdbcTemplate.query("SELECT * FROM EMPLOYEE_SERVICE.EMPLOYEE WHERE EMAIL=?",
                               new EmployeeMapper(), email);
       return employees.isEmpty() ? Optional.empty() : Optional.of(employees.get(0).getId());
    }

    @Override
    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM EMPLOYEE_SERVICE.EMPLOYEE WHERE `IS_ACTIVE` = TRUE",
                new EmployeeMapper());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("UPDATE EMPLOYEE_SERVICE.EMPLOYEE SET IS_ACTIVE = false WHERE id=?", id);
    }

    @Override
    public Employee findById(int id) {
        return jdbcTemplate.query("SELECT * FROM EMPLOYEE_SERVICE.EMPLOYEE WHERE IS_ACTIVE = true AND ID=?", new EmployeeMapper(), id)
                .stream().findAny().orElse(null);
    }

    @Override
    public void update(EmployeeRequest employeeRequest) {
        jdbcTemplate.update("UPDATE EMPLOYEE_SERVICE.EMPLOYEE SET EMPLOYEE_NAME =?, PHONE=?, EMAIL=?, DEPARTMENT_ID=?, DATE_OF_BIRTH=? WHERE ID=?",
                employeeRequest.getName(),
                employeeRequest.getPhone(),
                employeeRequest.getEmail(),
                employeeRequest.getDepartmentId(),
                employeeRequest.getDateOfBirth(),
                employeeRequest.getId()
        );
    }

    @Override
    public List<Employee> findByDepartmentId(int departmentId) {
        return jdbcTemplate.query("SELECT * FROM EMPLOYEE_SERVICE.EMPLOYEE WHERE `IS_ACTIVE` = TRUE AND DEPARTMENT_ID=?", new EmployeeMapper(),
                departmentId);
    }
}
