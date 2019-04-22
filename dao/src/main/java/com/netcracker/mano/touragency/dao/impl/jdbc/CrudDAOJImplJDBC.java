package com.netcracker.mano.touragency.dao.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class CrudDAOJImplJDBC {

    ConnectionPool connectionPool = ConnectionPool.getInstance();

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    void closeConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (connection != null) {
            connectionPool.returnConnection(connection);
        }

    }
}
