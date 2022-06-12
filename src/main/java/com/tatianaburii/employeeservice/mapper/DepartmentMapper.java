package com.tatianaburii.employeeservice.mapper;

import com.tatianaburii.employeeservice.domain.Department;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.parse;

public class DepartmentMapper implements RowMapper<Department> {

    @Override
    public Department mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Department department = new Department();
        department.setId(resultSet.getInt("ID"));
        department.setName(resultSet.getString("DEPARTMENT_NAME"));
        department.setCreatedAt(parse(resultSet.getString("CREATED_AT"), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        department.setActive(resultSet.getBoolean("IS_ACTIVE"));
       return department;
    }
}