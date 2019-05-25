package com.netcracker.mano.touragency.sql.scripts;

public class BookingScripts {
    public static final String SELECT_BY_ID = "SELECT * FROM bookings WHERE id=?";
    public static final String DELETE = "DELETE FROM bookings WHERE id=?";
    public static final String SELECT_ALL = "SELECT * FROM bookings";
    public static final String UPDATE = "UPDATE bookings b " +
            "SET b.number_of_clients=?, total_price=? WHERe b.id=?";
    public static final String CREATE = "INSERT INTO bookings (number_of_clients, total_price, user_id, tour_id, card_id)" +
            " VALUES(?,?,?,?,?)";
    public static final String SELECT_BY_CATEGORY = "SELECT * FROM bookings WHERE tour_id IN" +
            " (select t.id FROM tours t WHERE t.category_id in" +
            " (select category.id from category where category.userName=?))";
    public static final String SELECT_BY_USER_ID = "SELECT * FROM bookings WHERE user_id=?";
    public static final String SELECT_BY_USER_ID_AND_ID = "SELECT * FROM bookings WHERE id=? AND user_id=?";
}
