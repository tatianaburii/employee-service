package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.domain.Department;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)

public class JdbcDepartmentRepository implements DepartmentRepository {
    private static final String SQL_INSERT = "INSERT INTO DEPARTMENT (DEPARTMENT_NAME, CREATED_AT, IS_ACTIVE) VALUES(?,?,?)";
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
            if(resultSet.next()) {
                return resultSet.getInt("ID");
            }
            return -1;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}

