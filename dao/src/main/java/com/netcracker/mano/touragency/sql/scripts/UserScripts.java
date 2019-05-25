package com.netcracker.mano.touragency.sql.scripts;

public class UserScripts {
    public static final String INSERT = "INSERT INTO users" +
            "(userName,userSurname,isBlocked,role,credentials_id)" +
            "VALUES (?, ?, ?, ?, ?)";
    public static final String SELECT_BY_ID = "SELECT * FROM users JOIN credentials c " +
            "on users.credentials_id = c.id where users.id=?";
    public static final String UPDATE = "UPDATE users u" +
            " SET u.userName=?, u.userSurname=?, u.isBlocked=?, u.role=?, u.credentials_id=? WHERE id=?";
    public static final String SELECT_BY_CREDENTIALS = "SELECT * from users where credentials_id in" +
            " (select credentials.id from credentials where credentials.login=? and credentials.password=?)";
    public static final String SELECT_ALL = "SELECT * FROM users JOIN credentials c on users.credentials_id = c.id";
}
