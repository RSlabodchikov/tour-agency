package com.netcracker.mano.touragency.sql.scripts;

public class TourScripts {
    public static final String SELECT_BY_ID = "SELECT * FROM tours JOIN category c " +
            "on tours.category_id = c.id where tours.id=?";
    public static final String DELETE = "DELETE FROM tours WHERE id=?";
    public static final String SELECT_ALL = "SELECT * FROM tours JOIN category c" +
            " on tours.category_id=c.id";
    public static final String UPDATE = "UPDATE tours t SET t.price=?, t.vacant_places=? WHERE t.id=?";
    public static final String CREATE = "INSERT INTO tours (settlement_date, eviction_date, country_name, price, vacant_places, description, category_id) " +
            "VALUES (?,?,?,?,?,?,?)";
    public static final String GET_CATEGORY_ID="SELECT id FROM category WHERE userName=?";
}
