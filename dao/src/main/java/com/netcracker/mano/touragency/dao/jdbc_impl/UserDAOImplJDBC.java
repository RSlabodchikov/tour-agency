package com.netcracker.mano.touragency.dao.jdbc_impl;

import com.netcracker.mano.touragency.entity.User;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDAOImplJDBC {


    public UserDAOImplJDBC() {
    }

    public User getUserById(Long id) {
        User user = new User();
        try {
            Connection connection = ConnectionPool.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
