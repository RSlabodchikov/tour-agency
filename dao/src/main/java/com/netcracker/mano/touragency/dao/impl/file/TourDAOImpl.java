package com.netcracker.mano.touragency.dao.impl.file;

import com.netcracker.mano.touragency.dao.TourDAO;
import com.netcracker.mano.touragency.entity.Tour;
import org.springframework.beans.factory.annotation.Value;

import static com.netcracker.mano.touragency.constants.FileNames.TOUR_FILENAME;

public class TourDAOImpl extends CrudDAOImpl<Tour> implements TourDAO {
    @Value("${files.tour}")
    public static String filename;

    public TourDAOImpl() {
        super(TOUR_FILENAME);
    }
}
