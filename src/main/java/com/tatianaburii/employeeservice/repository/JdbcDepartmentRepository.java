package com.tatianaburii.employeeservice.repository;

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
    private static final String SQL_INSERT = "INSERT INTO DEPARTMENT (DEPARTMENT_NAME, CREATED_AT, IS_ACTIVE) VALUES(?,?,?)";
    private static final String SQL_SELECT = "SELECT ID, DEPARTMENT_NAME , CREATED_AT, IS_ACTIVE  FROM EMPLOYEE_SERVICE.DEPARTMENT where `IS_ACTIVE` = TRUE";
    private static final String SQL_DELETE = "UPDATE EMPLOYEE_SERVICE.DEPARTMENT SET IS_ACTIVE = false WHERE id = ?";
    Connection connect;

    @Override
    public void save(Department department) {
        try {
            PreparedStatement preparedStatement = connect.prepareStatement(SQL_INSERT);
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
            String sql = "SELECT ID FROM DEPARTMENT WHERE DEPARTMENT_NAME = '" + name + "'";
            ResultSet resultSet = statement.executeQuery(sql);
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
            ResultSet resultSet = statement.executeQuery(SQL_SELECT);
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
            PreparedStatement preparedStatement = connect.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

