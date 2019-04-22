package com.netcracker.mano.touragency.dao.impl.file;

import com.netcracker.mano.touragency.dao.CreditCardDAO;
import com.netcracker.mano.touragency.entity.CreditCard;

import java.util.List;

import static com.netcracker.mano.touragency.constants.FileNames.CARD_FILENAME;

public class CreditCardDAOImpl extends CrudDAOImpl<CreditCard> implements CreditCardDAO {
    public CreditCardDAOImpl() {
        super(CARD_FILENAME);
    }

    @Override
    public List<CreditCard> getAllClientCards(Long clientId) {
        return null;
    }
}
