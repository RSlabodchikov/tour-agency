package com.netcracker.mano.touragency.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Credentials {
    Long id;
    String login;
    String password;

    public Credentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void extractResult(ResultSet resultSet) throws SQLException {
        setId(resultSet.getLong(7));
        setLogin(resultSet.getString(8));
        setPassword(resultSet.getString(9));
    }

    public void setStatementParams(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
    }
}