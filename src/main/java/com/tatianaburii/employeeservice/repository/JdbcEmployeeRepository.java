package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.controller.dto.EmployeeRequest;
import com.tatianaburii.employeeservice.domain.Employee;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
            String query = "INSERT INTO EMPLOYEE (EMPLOYEE_NAME, EMAIL, PHONE, CREATED_AT, IS_ACTIVE, DEPARTMENT_ID) VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getEmail());
            preparedStatement.setString(3, employee.getPhone());
            preparedStatement.setString(4, employee.getCreatedAt().toString());
            preparedStatement.setBoolean(5, employee.isActive());
            preparedStatement.setInt(6, employee.getDepartmentId());
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
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            Statement statement = connect.createStatement();
            String query = "SELECT * FROM EMPLOYEE_SERVICE.EMPLOYEE WHERE `IS_ACTIVE` = TRUE";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("EMPLOYEE_NAME");
                String phone = resultSet.getString("PHONE");
                String email = resultSet.getString("EMAIL");
                LocalDateTime createdAt = LocalDateTime.parse(resultSet.getString("CREATED_AT"));
                boolean active = resultSet.getBoolean("IS_ACTIVE");
                int departmentId = resultSet.getInt("DEPARTMENT_ID");
                employees.add(new Employee(id, name, phone, email, createdAt, active, departmentId));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
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
                int employeeId = resultSet.getInt("ID");
                String name = resultSet.getString("EMPLOYEE_NAME");
                String phone = resultSet.getString("PHONE");
                String email = resultSet.getString("EMAIL");
                LocalDateTime createdAt = LocalDateTime.parse(resultSet.getString("CREATED_AT"));
                boolean active = resultSet.getBoolean("IS_ACTIVE");
                int departmentId = resultSet.getInt("DEPARTMENT_ID");
                return new Employee(employeeId, name, phone, email, createdAt, active, departmentId);
            }
            statement.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(EmployeeRequest employeeRequest) {
        try {
            String query = "UPDATE EMPLOYEE_SERVICE.EMPLOYEE SET EMPLOYEE_NAME =?, PHONE=?, EMAIL=?, DEPARTMENT_ID=? WHERE ID=?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, employeeRequest.getName());
            preparedStatement.setString(2, employeeRequest.getPhone());
            preparedStatement.setString(3, employeeRequest.getEmail());
            preparedStatement.setInt(4, employeeRequest.getDepartmentId());
            preparedStatement.setInt(5, employeeRequest.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> findByDepartmentId(int departmentId) {
        ArrayList<Employee> employees = new ArrayList<>();
        try {
            Statement statement = connect.createStatement();
            String query = "SELECT * FROM EMPLOYEE_SERVICE.EMPLOYEE WHERE DEPARTMENT_ID='" + departmentId + "'";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("EMPLOYEE_NAME");
                String phone = resultSet.getString("PHONE");
                String email = resultSet.getString("EMAIL");
                LocalDateTime createdAt = LocalDateTime.parse(resultSet.getString("CREATED_AT"));
                boolean active = resultSet.getBoolean("IS_ACTIVE");
                int departmentID = resultSet.getInt("DEPARTMENT_ID");
                employees.add(new Employee(id, name, phone, email, createdAt, active, departmentID));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
