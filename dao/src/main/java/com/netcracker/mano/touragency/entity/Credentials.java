package com.netcracker.mano.touragency.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "credentials", schema = "tour_agency")
public class Credentials {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

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