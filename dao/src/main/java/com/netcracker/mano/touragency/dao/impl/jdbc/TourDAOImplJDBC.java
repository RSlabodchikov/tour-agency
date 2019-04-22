package com.netcracker.mano.touragency.dao.impl.jdbc;

import com.netcracker.mano.touragency.dao.TourDAO;
import com.netcracker.mano.touragency.entity.Tour;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.sql.scripts.TourScripts;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class TourDAOImplJDBC extends CrudDAOJImplJDBC implements TourDAO {
    private static TourDAOImplJDBC instance;

    private TourDAOImplJDBC() {
    }

    public static TourDAOImplJDBC getInstance() {
        if (instance == null) {
            instance = new TourDAOImplJDBC();
        }
        return instance;
    }

    @Override
    public Tour getById(long id) throws EntityNotFoundException {
        Tour tour = new Tour();
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(TourScripts.SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                tour.extractResult(resultSet);
            } else throw new EntityNotFoundException();
        } catch (SQLException e) {
            log.error(e.getSQLState());
            throw new EntityNotFoundException();
        } finally {
            closeConnection();
        }
        return tour;
    }

    @Override
    public Tour add(Tour entity) throws CannotCreateEntityException {
        try {
            connection = connectionPool.getConnection();
            connection.setAutoCommit(false);
            long category_id;
            preparedStatement = connection.prepareStatement(TourScripts.GET_CATEGORY_ID);
            preparedStatement.setString(1, entity.getCategory().toString());
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                category_id = resultSet.getLong(1);
            else throw new CannotCreateEntityException();
            preparedStatement = connection.prepareStatement(TourScripts.CREATE, Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParamsToCreate(preparedStatement);
            preparedStatement.setLong(7, category_id);
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
            } else throw new CannotCreateEntityException();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    log.error(e1.getSQLState());
                }
            }
            log.error(e.getSQLState());
            throw new CannotCreateEntityException();
        } finally {
            closeConnection();
        }
        return entity;
    }

    @Override
    public Tour update(Tour entity) throws CannotUpdateEntityException {
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(TourScripts.UPDATE, Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParamsToChange(preparedStatement);
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.extractResult(resultSet);
            } else throw new CannotUpdateEntityException();
        } catch (SQLException e) {
            log.error(e.getSQLState());
            throw new CannotUpdateEntityException();
        } finally {
            closeConnection();
        }
        return entity;
    }

    @Override
    public void delete(long id) {
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(TourScripts.DELETE);
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error(e.getSQLState());
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<Tour> getAll() {
        List<Tour> tours = new ArrayList<>();
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(TourScripts.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Tour tour = new Tour();
                tour.extractResult(resultSet);
                tours.add(tour);
            }
        } catch (SQLException e) {
            log.error(e.getSQLState());
        } finally {
            closeConnection();
        }
        return tours;
    }
}
