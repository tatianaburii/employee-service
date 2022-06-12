package com.tatianaburii.employeeservice.mapper;

import com.tatianaburii.employeeservice.domain.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.parse;

public class EmployeeMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getInt("ID"));
        employee.setName(resultSet.getString("EMPLOYEE_NAME"));
        employee.setPhone(resultSet.getString("PHONE"));
        employee.setEmail(resultSet.getString("EMAIL"));
        employee.setCreatedAt(parse(resultSet.getString("CREATED_AT"), DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        employee.setActive(resultSet.getBoolean("IS_ACTIVE"));
        employee.setDepartmentId(resultSet.getInt("DEPARTMENT_ID"));
        employee.setDateOfBirth(LocalDate.parse(resultSet.getString("DATE_OF_BIRTH"), DateTimeFormatter.ISO_LOCAL_DATE));
        return employee;
    }
}