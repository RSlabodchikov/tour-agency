package com.netcracker.mano.touragency.sql.scripts;

public class CreditCardsScripts {
    public static final String SELECT_BY_ID = "SELECT * FROM credit_cards WHERE id=?";
    public static final String DELETE = "DELETE FROM credit_cards WHERE id=?";
    public static final String SELECT_ALL = "SELECT * FROM credit_cards";
    public static final String UPDATE = "UPDATE credit_cards  SET number=?, balance=?, user_id=? WHERE id=?";
    public static final String SELECT_BY_USER_ID = "SELECT * FROM credit_cards where user_id=?";
    public static final String CREATE = "INSERT INTO credit_cards (number, balance, user_id) VALUES (?,?,?)";
    public static final String SELECT_BY_USER_AND_ID = "SELECT * FROM credit_cards WHERE user_id=? and id=?";
}
