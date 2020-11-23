package com.epam.homework.db;

import com.epam.homework.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        try {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setEmail(resultSet.getString("email"));
            user.setIdn(resultSet.getLong("idn"));
            user.setBlocked(resultSet.getBoolean("block"));
            user.setUserRoleId(resultSet.getInt("user_role_id"));
            user.setPassword(resultSet.getString("password"));
            return user;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}