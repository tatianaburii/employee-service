package com.tatianaburii.employeeservice.controller;

import com.tatianaburii.employeeservice.exceptions.DepartmentNotFoundException;
import com.tatianaburii.employeeservice.exceptions.EmployeeNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    public String databaseError() {
        log.error("DB connection exception");
        return "db-error";
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public String departmentNotFound(DepartmentNotFoundException exception) {
        log.warn(exception.getMessage());
        return "not-found-department";
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public String employeeNotFound(DepartmentNotFoundException exception) {
        log.warn(exception.getMessage());
        return "not-found-employee";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception ex) {
        log.error(ex.getMessage(), ex);
        return "general_error";
    }
}
