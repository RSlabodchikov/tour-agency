package com.netcracker.mano.touragency.dao.impl.jdbc;

import com.netcracker.mano.touragency.dao.UserDAO;
import com.netcracker.mano.touragency.entity.Credentials;
import com.netcracker.mano.touragency.entity.User;
import com.netcracker.mano.touragency.exceptions.AuthorizationException;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.sql.scripts.CredentialsScripts;
import com.netcracker.mano.touragency.sql.scripts.UserScripts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class UserDAOImplJDBC extends CrudDAOJImplJDBC implements UserDAO {
    @Override
    public User getById(long id) throws EntityNotFoundException {

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

            } else throw new EntityNotFoundException();
        } catch (SQLException e) {
            log.error("Cannot get user by id", e);
            throw new EntityNotFoundException();
        } finally {
            closeConnection();
        }
        log.info("Get user from database :{}", user);
        return user;
    }

    @Override
    public User findUserByCredentials(Credentials credentials) throws AuthorizationException {
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
            } else throw new SQLException();
            user.setCredentials(credentials);
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            log.error("Cannot find user", e);
            throw new AuthorizationException();
        } finally {
            closeConnection();
        }
        log.info("Get user by credentials from db :{}", user);
        return user;
    }

    @Override
    public Credentials findCredentialsByLogin(String login) throws EntityNotFoundException {
        Credentials credentials = new Credentials();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CredentialsScripts.SELECT_BY_LOGIN);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                credentials.extractResult(resultSet);

            } else throw new EntityNotFoundException();
        } catch (SQLException e) {
            log.error("Cannot find credentials", e);
        } finally {
            closeConnection();
        }
        log.info("Found credentials :{}", credentials);
        return credentials;
    }

    @Override
    public User add(User entity) throws CannotCreateEntityException {
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
                throw new CannotCreateEntityException();
            }
            preparedStatement = connection.prepareStatement(UserScripts.INSERT,
                    Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParams(preparedStatement);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
            } else {
                throw new CannotCreateEntityException();
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException exception) {
                log.error("Cannot rollback ", exception);
            }
            log.error("Cannot create user", e);
            throw new CannotCreateEntityException();
        } finally {
            closeConnection();
        }
        log.info("Created new user :{}", entity);
        return entity;
    }

    @Override
    public User update(User entity) throws CannotUpdateEntityException {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(UserScripts.UPDATE, Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParams(preparedStatement);
            preparedStatement.setLong(6, entity.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error(e.getSQLState());
            throw new CannotUpdateEntityException();
        } finally {
            closeConnection();
        }
        log.info("Updated user entity :{}", entity);
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
            log.error("Cannot change user password", e);
        } finally {
            closeConnection();
        }
        log.info("User password changed");
    }

    @Override
    public void delete(long id) {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CredentialsScripts.DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Cannot delete user", e);
        } finally {
            closeConnection();
        }
        log.info("User delete from database with id :{}", id);
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
            log.error("Cannot get users", e);
        } finally {
            closeConnection();
        }
        log.info("Get all users from db :{}", users);
        return users;
    }
}



