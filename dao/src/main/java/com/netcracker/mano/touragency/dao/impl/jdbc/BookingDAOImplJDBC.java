package com.netcracker.mano.touragency.dao.impl.jdbc;

import com.netcracker.mano.touragency.dao.BookingDAO;
import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.sql.scripts.BookingScripts;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImplJDBC extends CrudDAOJImplJDBC implements BookingDAO {
    private final Logger logger = Logger.getLogger(BookingDAOImplJDBC.class);

    private static BookingDAOImplJDBC instance;

    private BookingDAOImplJDBC() {
    }

    public static BookingDAOImplJDBC getInstance() {
        if (instance == null) {
            instance = new BookingDAOImplJDBC();
        }
        return instance;
    }

    @Override
    public Booking getById(long id) {
        Booking booking = new Booking();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BookingScripts.SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                booking.extractResult(resultSet);
            } else return null;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
        return booking;
    }

    @Override
    public Booking add(Booking entity) {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BookingScripts.CREATE, Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParams(preparedStatement);
            preparedStatement.setLong(3, entity.getUserId());
            preparedStatement.setLong(4, entity.getTourId());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
            } else return null;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
        return entity;
    }

    @Override
    public Booking update(Booking entity) {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BookingScripts.UPDATE, Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParams(preparedStatement);
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.extractResult(resultSet);
            } else return null;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
        return entity;
    }

    @Override
    public void delete(long id) {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BookingScripts.DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }

    }

    @Override
    public List<Booking> getAll() {
        List<Booking> bookings = new ArrayList<>();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BookingScripts.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.extractResult(resultSet);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
        return bookings;
    }

    @Override
    public List<Booking> getAllByCategory(String category) {
        List<Booking> bookings = new ArrayList<>();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BookingScripts.SELECT_BY_CATEGORY);
            preparedStatement.setString(1, category);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.extractResult(resultSet);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
        return bookings;
    }
}
