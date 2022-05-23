package com.tatianaburii.employeeservice.repository;

import com.tatianaburii.employeeservice.domain.Employee;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.sql.*;

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
            String query = "SELECT ID FROM EMPLOYEE WHERE EMAIL = '" + email + "'";
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

}
