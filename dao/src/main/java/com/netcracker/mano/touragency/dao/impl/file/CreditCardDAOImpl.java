package com.netcracker.mano.touragency.dao.impl.file;

import com.netcracker.mano.touragency.dao.CreditCardDAO;
import com.netcracker.mano.touragency.entity.CreditCard;
import com.netcracker.mano.touragency.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.netcracker.mano.touragency.constants.FileNames.CARD_FILENAME;

public class CreditCardDAOImpl extends CrudDAOImpl<CreditCard> implements CreditCardDAO {
    @Value("${files.credit-card}")
    public static String filename;
    public CreditCardDAOImpl() {
        super(CARD_FILENAME);
    }

    @Override
    public List<CreditCard> getAllClientCards(Long clientId) {
        return null;
    }

    @Override
    public CreditCard getClientCard(Long id, Long clientId) throws EntityNotFoundException {
        return null;
    }
}
