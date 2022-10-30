package com.tatianaburii.employeeservice.api;

import com.tatianaburii.employeeservice.api.exceptions.DepartmentNotFoundException;
import com.tatianaburii.employeeservice.api.exceptions.EmployeeNotFoundException;
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
        return "not-found-error";
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public String employeeNotFound(EmployeeNotFoundException exception) {
        log.warn(exception.getMessage());
        return "not-found-error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneralError(Exception ex) {
        log.info(ex.getMessage(), ex);
        return "error";
    }
}
