package com.netcracker.mano.touragency.dao.impl.jdbc;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Slf4j
class CrudDAOJImplJDBC {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    void closeConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            log.error("Cannot close result set", e);
        }
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            log.error("Cannot close prepared statement", e);
        }

        if (connection != null) {
            ConnectionPool.returnConnection(connection);
        }

    }
}
