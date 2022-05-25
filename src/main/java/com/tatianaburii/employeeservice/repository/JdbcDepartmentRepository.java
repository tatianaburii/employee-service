package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.controller.dto.DepartmentRequest;
import com.tatianaburii.employeeservice.domain.Department;
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

public class JdbcDepartmentRepository implements DepartmentRepository {
    Connection connect;

    @Override
    public void save(Department department) {
        try {
            String query = "INSERT INTO DEPARTMENT (DEPARTMENT_NAME, CREATED_AT, IS_ACTIVE) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, department.getName());
            preparedStatement.setString(2, department.getCreatedAt().toString());
            preparedStatement.setBoolean(3, department.isActive());
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public int findIdByName(String name) {
        try {
            Statement statement = connect.createStatement();
            String query = "SELECT ID FROM DEPARTMENT WHERE `IS_ACTIVE` = TRUE AND DEPARTMENT_NAME = '" + name + "'";
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
    public List<Department> findAll() {
        ArrayList<Department> departments = new ArrayList<>();
        try {
            Statement statement = connect.createStatement();
            String query = "SELECT * FROM EMPLOYEE_SERVICE.DEPARTMENT WHERE `IS_ACTIVE` = TRUE";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("DEPARTMENT_NAME");
                LocalDateTime createdAt = LocalDateTime.parse(resultSet.getString("CREATED_AT"));
                boolean active = resultSet.getBoolean("IS_ACTIVE");
                departments.add(new Department(id, name, createdAt, active));
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }

    @Override
    public void delete(int id) {
        try {
            String query = "UPDATE EMPLOYEE_SERVICE.DEPARTMENT SET IS_ACTIVE = false WHERE id = ?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Department findById(int id) {
        try {
            Statement statement = connect.createStatement();
            String sql = "SELECT * FROM DEPARTMENT WHERE ID = '" + id + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                int departmentId = resultSet.getInt("ID");
                String name = resultSet.getString("DEPARTMENT_NAME");
                LocalDateTime createdAt = LocalDateTime.parse(resultSet.getString("CREATED_AT"));
                boolean active = resultSet.getBoolean("IS_ACTIVE");
                return new Department(departmentId, name, createdAt, active);
            }
            statement.close();
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void update(DepartmentRequest departmentRequest) {
        try {
            String query = "UPDATE EMPLOYEE_SERVICE.DEPARTMENT SET DEPARTMENT_NAME=? WHERE id=?";
            PreparedStatement preparedStatement = connect.prepareStatement(query);
            preparedStatement.setString(1, departmentRequest.getName());
            preparedStatement.setInt(2, departmentRequest.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

