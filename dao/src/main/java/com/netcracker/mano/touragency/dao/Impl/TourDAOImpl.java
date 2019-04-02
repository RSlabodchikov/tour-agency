package com.netcracker.mano.touragency.dao.Impl;

import com.netcracker.mano.touragency.dao.TourDAO;
import com.netcracker.mano.touragency.entity.Tour;

import static com.netcracker.mano.touragency.constants.FileNames.TOUR_FILENAME;

public class TourDAOImpl extends CrudDAOImpl<Tour> implements TourDAO {
    public TourDAOImpl() {
        super(TOUR_FILENAME);
    }
}
