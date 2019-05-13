package com.netcracker.mano.touragency.dao.impl.jdbc;

import com.netcracker.mano.touragency.dao.BookingDAO;
import com.netcracker.mano.touragency.entity.Booking;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.sql.scripts.BookingScripts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class BookingDAOImplJDBC extends CrudDAOJImplJDBC implements BookingDAO {
    @Override
    public Boolean checkExist(Long id, Long clientId) {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BookingScripts.SELECT_BY_USER_ID_AND_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, clientId);
            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            log.error("Cannot find booking", e);
            return false;
        } finally {
            closeConnection();
        }
    }

    @Override
    public Booking getById(long id) throws EntityNotFoundException {
        Booking booking = new Booking();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BookingScripts.SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                booking.extractResult(resultSet);
            } else throw new SQLException();
        } catch (SQLException e) {
            log.error("Cannot get booking by id", e);
            throw new EntityNotFoundException();
        } finally {
            closeConnection();
        }
        log.info("Found booking : {}", booking);
        return booking;
    }

    @Override
    public Booking add(Booking entity) throws CannotCreateEntityException {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BookingScripts.CREATE, Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParams(preparedStatement);
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
            } else throw new SQLException();
        } catch (SQLException e) {
            log.error("Cannot create booking", e);
            throw new CannotCreateEntityException();
        } finally {
            closeConnection();
        }
        log.info("Created new booking :{}", entity);
        return entity;
    }

    @Override
    public Booking update(Booking entity) throws CannotUpdateEntityException {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BookingScripts.UPDATE, Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParams(preparedStatement);
            preparedStatement.setLong(3, entity.getId());
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.extractResult(resultSet);
            } else throw new SQLException();
        } catch (SQLException e) {
            log.error("Cannot update booking", e);
            throw new CannotUpdateEntityException();
        } finally {
            closeConnection();
        }
        log.info("Booking updated :{}", entity);
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
            log.error("Cannot delete booking", e);
        } finally {
            closeConnection();
        }
        log.info("Booking deleted from db with id :{}", id);
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
            log.error("Cannot get all bookings", e);
        } finally {
            closeConnection();
        }
        log.info("Get all bookings :{}", bookings);
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
            log.error("Cannot get bookings", e);
        } finally {
            closeConnection();
        }
        log.info("Get all bookings by category :{}", bookings);
        return bookings;
    }

    @Override
    public List<Booking> getAllClientBookings(long clientId) {
        List<Booking> bookings = new ArrayList<>();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BookingScripts.SELECT_BY_USER_ID);
            preparedStatement.setLong(1, clientId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Booking booking = new Booking();
                booking.extractResult(resultSet);
                bookings.add(booking);
            }
        } catch (SQLException e) {
            log.error("Cannot get client bookings", e);
        } finally {
            closeConnection();
        }
        return bookings;
    }

    @Override
    public Booking findBookingByClientIdAndId(long id, long userId) throws EntityNotFoundException {
        Booking booking = new Booking();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(BookingScripts.SELECT_BY_USER_ID_AND_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, userId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                booking.extractResult(resultSet);
            } else throw new EntityNotFoundException();
        } catch (SQLException e) {
            log.error("Cannot find booking", e);
        } finally {
            closeConnection();
        }
        return booking;
    }

}
