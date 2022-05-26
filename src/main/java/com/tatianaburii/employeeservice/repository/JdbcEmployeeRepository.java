package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Employee;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class JdbcEmployeeRepository implements EmployeeRepository {
    Connection connect;

    @Override
    public void save(Employee employee) {
        try {
            String query = "INSERT INTO EMPLOYEE (EMPLOYEE_NAME, EMAIL, PHONE, CREATED_AT, DATE_OF_BIRTH, IS_ACTIVE, DEPARTMENT_ID) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getPhone());
            preparedStatement.setString(4, employee.getCreatedAt().toString());
            preparedStatement.setString(5, employee.getDateOfBirth().toString());
            preparedStatement.setBoolean(6, employee.isActive());
            preparedStatement.setInt(7, employee.getDepartmentId());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int findIdByEmail(String email) {
        try {
            Statement statement = connect.createStatement();
            String query = "SELECT ID FROM EMPLOYEE WHERE `IS_ACTIVE` = TRUE AND EMAIL = '" + email + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getInt("ID");
            }
            statement.close();
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<Employee> findAll() {
        try {
            Statement statement = connect.createStatement();
            String query = "SELECT * FROM EMPLOYEE_SERVICE.EMPLOYEE WHERE `IS_ACTIVE` = TRUE";
            ResultSet resultSet = statement.executeQuery(query);
            List<Employee> employees = mapEmployees(resultSet);
            statement.close();
            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void delete(int id) {
        try {
            String query = "UPDATE EMPLOYEE_SERVICE.EMPLOYEE SET IS_ACTIVE = false WHERE id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employee findById(int id) {
        try {
            Statement statement = connect.createStatement();
            String sql = "SELECT * FROM EMPLOYEE_SERVICE.EMPLOYEE WHERE ID = '" + id + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return mapEmployee(resultSet);
            }
            statement.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Employee mapEmployee(ResultSet resultSet) throws SQLException {
        int employeeId = resultSet.getInt("ID");
        String name = resultSet.getString("EMPLOYEE_NAME");
        String phone = resultSet.getString("PHONE");
        String email = resultSet.getString("EMAIL");
        LocalDateTime createdAt = LocalDateTime.parse(resultSet.getString("CREATED_AT"));
        boolean active = resultSet.getBoolean("IS_ACTIVE");
        int departmentId = resultSet.getInt("DEPARTMENT_ID");
        LocalDate dateOfBirth = LocalDate.parse(resultSet.getString("DATE_OF_BIRTH"));
        return new Employee(employeeId, name, phone, email, createdAt, active, departmentId, dateOfBirth);
    }

    @Override
    public void update(EmployeeRequest employeeRequest) {
        try {
            String query = "UPDATE EMPLOYEE_SERVICE.EMPLOYEE SET EMPLOYEE_NAME =?, PHONE=?, EMAIL=?, DEPARTMENT_ID=?, DATE_OF_BIRTH=? WHERE ID=?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, employeeRequest.getName());
            preparedStatement.setString(2, employeeRequest.getPhone());
            preparedStatement.setString(3, employeeRequest.getEmail());
            preparedStatement.setInt(4, employeeRequest.getDepartmentId());
            preparedStatement.setString(5, employeeRequest.getDateOfBirth().toString());
            preparedStatement.setInt(6, employeeRequest.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> findByDepartmentId(int departmentId) {
        try {
            Statement statement = connect.createStatement();
            String query = "SELECT * FROM EMPLOYEE_SERVICE.EMPLOYEE WHERE `IS_ACTIVE` = TRUE AND DEPARTMENT_ID='" + departmentId + "'";
            ResultSet resultSet = statement.executeQuery(query);
            List<Employee> employees = mapEmployees(resultSet);
            statement.close();
            return employees;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<Employee> mapEmployees(ResultSet resultSet) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(mapEmployee(resultSet));
        }
        return employees;
    }
}
