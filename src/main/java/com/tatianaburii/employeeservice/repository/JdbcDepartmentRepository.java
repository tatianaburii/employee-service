package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;
import com.tatianaburii.employeeservice.mapper.DepartmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class JdbcDepartmentRepository implements DepartmentRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcDepartmentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Department department) {
        jdbcTemplate.update("INSERT INTO EMPLOYEE_SERVICE.DEPARTMENT (DEPARTMENT_NAME, CREATED_AT, IS_ACTIVE) VALUES(?,?,?)",
                department.getName(), department.getCreatedAt().toString(), department.isActive());
    }

    @Override
    public Optional<Integer> findIdByParam(String name) {
        List<Department> departments = jdbcTemplate.query("SELECT * FROM EMPLOYEE_SERVICE.DEPARTMENT WHERE IS_ACTIVE = true AND DEPARTMENT_NAME=?",
                new DepartmentMapper(), name);
        return departments.isEmpty() ? Optional.empty() : Optional.of(departments.get(0).getId());
    }

    @Override
    public List<Department> findAll() {
        return jdbcTemplate.query("SELECT * FROM EMPLOYEE_SERVICE.DEPARTMENT WHERE `IS_ACTIVE` = TRUE",
                new DepartmentMapper());
    }

    @Override
    public void delete(int id) {
        jdbcTemplate.update("UPDATE EMPLOYEE_SERVICE.DEPARTMENT SET IS_ACTIVE = false WHERE id=?", id);

    }

    @Override
    public void update(DepartmentRequest departmentRequest) {
        jdbcTemplate.update("UPDATE EMPLOYEE_SERVICE.DEPARTMENT SET DEPARTMENT_NAME=? WHERE id=?",
                departmentRequest.getName(), departmentRequest.getId());
    }

    @Override
    public Department findById(int id) {
        return jdbcTemplate.query("SELECT * FROM EMPLOYEE_SERVICE.DEPARTMENT WHERE ID=?", new DepartmentMapper(), id)
                .stream().findAny().orElse(null);
    }
}

