package com.netcracker.mano.touragency.dao.impl.jdbc;

import com.netcracker.mano.touragency.dao.CreditCardDAO;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.CannotCreateEntityException;
import com.netcracker.mano.touragency.exceptions.CannotUpdateEntityException;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import com.netcracker.mano.touragency.sql.scripts.CreditCardsScripts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CreditCardDAOImplJDBC extends CrudDAOJImplJDBC implements CreditCardDAO {

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
    public CreditCard getById(long id) throws EntityNotFoundException {
        CreditCard card = new CreditCard();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CreditCardsScripts.SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                card.extractResult(resultSet);
            } else throw new SQLException();
        } catch (SQLException e) {
            log.error("Cannot get card by id", e);
            throw new EntityNotFoundException();
        } finally {
            closeConnection();
        }
        log.info("Get card from db:{}", card);
        return card;
    }

    @Override
    public CreditCard add(CreditCard entity) throws CannotCreateEntityException {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CreditCardsScripts.CREATE, Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParams(preparedStatement);
            preparedStatement.execute();
            resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
            } else throw new SQLException();
        } catch (SQLException e) {
            log.error("Cannot create card", e);
            throw new CannotCreateEntityException();
        } finally {
            closeConnection();
        }
        log.info("Created new card:{}", entity);
        return entity;
    }

    @Override
    public CreditCard update(CreditCard entity) throws CannotUpdateEntityException {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CreditCardsScripts.UPDATE, Statement.RETURN_GENERATED_KEYS);
            entity.setStatementParams(preparedStatement);
            preparedStatement.setLong(4, entity.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Cannot update card", e);
            throw new CannotUpdateEntityException();
        } finally {
            closeConnection();
        }
        log.info("Updated card :{}", entity);
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
            log.error("Cannot delete card", e);
        } finally {
            closeConnection();
        }
        log.info("Card deleted from db with id :{}", id);
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
            log.error(e.getSQLState());
        } finally {
            closeConnection();
        }
        log.info("Get all cards from db :{}", cards);
        return cards;
    }

    @Override
    public List<CreditCard> getAllClientCards(Long clientId) throws EntityNotFoundException {
        List<CreditCard> clientCards = new ArrayList<>();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(CreditCardsScripts.SELECT_BY_USER_ID);
            preparedStatement.setLong(1, clientId);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CreditCard card = new CreditCard();
                card.extractResult(resultSet);
                clientCards.add(card);
            }
        } catch (SQLException e) {
            log.error("Cannot get cards", e);
            throw new EntityNotFoundException();
        } finally {
            closeConnection();
        }
        if (clientCards.size() == 0) throw new EntityNotFoundException();
        log.info("Get all client cards from db :{}", clientCards);
        return clientCards;
    }
}
