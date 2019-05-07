package com.netcracker.mano.touragency.sql.scripts;

public class CredentialsScripts {
    public static final String INSERT = "INSERT INTO credentials" +
            "(login,password) " +
            "VALUES (?, ?)";
    public static final String SELECT_BY_LOGIN = "SELECT * FROM credentials WHERE login=?";
    public static final String UPDATE = "UPDATE credentials c set password=? where login=?";
    public static final String DELETE = "DELETE FROM credentials" +
            " where credentials.id IN (select u.credentials_id from users u where u.id=?)";

}
