package com.netcracker.mano.touragency.dao.impl.jdbc;


import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

@Slf4j
class ConnectionPool {
    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }


    private Vector<Connection> connectionPool = new Vector<>();

    private ConnectionPool() {
        initialize();
    }

    private void initialize() {
        while (!checkIfConnectionPoolIsFull()) {
            connectionPool.addElement(createNewConnectionForPool());
        }
        log.info("Connection pool successfully initialized with 10 connections ");
    }

    private Connection createNewConnectionForPool() {
        Connection connection;
        String databaseUrl = "jdbc:mysql://localhost:3306/tour_agency?useSSL=false";
        String userName = "root";
        String password = "bananjikda1";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(databaseUrl, userName, password);
        } catch (SQLException sqlExc) {
            log.error(sqlExc.getSQLState());
            return null;
        } catch (ClassNotFoundException exception) {
            log.error(exception.getLocalizedMessage());
            return null;
        }

        return connection;
    }

    private boolean checkIfConnectionPoolIsFull() {
        return connectionPool.size() >= 10;
    }

    synchronized Connection getConnection() {
        Connection connection = null;
        if (connectionPool.size() > 0) {
            connection = connectionPool.firstElement();
            connectionPool.removeElementAt(0);
        }
        return connection;
    }

    synchronized void returnConnection(Connection connection) {
        connectionPool.addElement(connection);
    }
}
