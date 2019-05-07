package com.netcracker.mano.touragency.dao.impl.jdbc;

import com.netcracker.mano.touragency.dao.CreditCardDAO;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.sql.scripts.CreditCardsScripts;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CreditCardDAOImplJDBC extends CrudDAOJImplJDBC implements CreditCardDAO {
    private final Logger logger = Logger.getLogger(CreditCardDAOImplJDBC.class);

    private static CreditCardDAOImplJDBC instance;

    private CreditCardDAOImplJDBC() {
    }

    public static CreditCardDAOImplJDBC getInstance() {
        if (instance == null) {
            instance = new CreditCardDAOImplJDBC();
        }
        return instance;
    }

    @Override
    public CreditCard getById(long id) {
        CreditCard card = new CreditCard();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CreditCardsScripts.SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                card.extractResult(resultSet);
            } else return null;
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
        return card;
    }

    @Override
    public CreditCard add(CreditCard entity) {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CreditCardsScripts.CREATE, Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParams(preparedStatement);
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
    public CreditCard update(CreditCard entity) {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CreditCardsScripts.UPDATE, Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParams(preparedStatement);
            preparedStatement.setLong(4, entity.getId());
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
            preparedStatement = connection.prepareStatement(CreditCardsScripts.DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
    }

    @Override
    public List<CreditCard> getAll() {
        List<CreditCard> cards = new ArrayList<>();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CreditCardsScripts.SELECT_ALL);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CreditCard card = new CreditCard();
                card.extractResult(resultSet);
                cards.add(card);
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            closeConnection();
        }
        return cards;
    }
}
