package com.netcracker.mano.touragency.dao.impl.jdbc;

import com.netcracker.mano.touragency.dao.UserDAO;
import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.sql.scripts.CredentialsScripts;
import com.netcracker.mano.touragency.sql.scripts.UserScripts;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class UserDAOImplJDBC extends CrudDAOJImplJDBC implements UserDAO {
    private final Logger logger = Logger.getLogger(UserDAOImplJDBC.class);
    private static UserDAOImplJDBC instance;

    private UserDAOImplJDBC() {
    }

    public static UserDAOImplJDBC getInstance() {
        if (instance == null) {
            instance = new UserDAOImplJDBC();
        }
        return instance;
    }

    @Override
    public User getById(long id) {

        User user = new User();
        Credentials credentials = new Credentials();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(UserScripts.SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.extractResult(resultSet);
                credentials.extractResult(resultSet);
                user.setCredentials(credentials);

            } else return null;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
        return user;
    }

    @Override
    public User findUserByCredentials(Credentials credentials) {
        User user = new User();
        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(UserScripts.SELECT_BY_CREDENTIALS);
            credentials.setStatementParams(preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user.extractResult(resultSet);
                credentials.setId(resultSet.getLong(6));
            } else return null;
            user.setCredentials(credentials);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            logger.error(e);
        } finally {
            closeConnection();
        }
        return user;
    }

    @Override
    public Boolean checkUserIfExist(String login) {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CredentialsScripts.SELECT_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
        return false;
    }

    @Override
    public User add(User entity) {
        try {
            connection = ConnectionPool.getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(CredentialsScripts.INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            entity.getCredentials().setStatementParams(preparedStatement);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.getCredentials().setId(resultSet.getLong(1));
            } else {
                return null;
            }
            preparedStatement = connection.prepareStatement(UserScripts.INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParams(preparedStatement);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
            } else {
                return null;
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            logger.error(e);
        } finally {
            closeConnection();
        }
        return entity;
    }

    @Override
    public User update(User entity) {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(UserScripts.UPDATE, Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParams(preparedStatement);
            preparedStatement.setLong(6, entity.getId());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.extractResult(resultSet);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
        return entity;
    }

    @Override
    public void changePassword(String login, String password) {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CredentialsScripts.UPDATE);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, login);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(long id) {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CredentialsScripts.DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }

    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();

        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(UserScripts.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.extractResult(resultSet);
                Credentials credentials = new Credentials();
                credentials.extractResult(resultSet);
                user.setCredentials(credentials);

                users.add(user);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
        return users;
    }
}



