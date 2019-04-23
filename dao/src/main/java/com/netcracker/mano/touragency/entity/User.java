package com.netcracker.mano.touragency.entity;

import lombok.*;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@AllArgsConstructor
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Component
public class User extends BaseEntity {
    private Credentials credentials;
    private String name;
    private String surname;
    private Role role;
    private Boolean isBlocked;


    public void extractResult(ResultSet resultSet) throws SQLException {
        setId(resultSet.getLong(1));
        setName(resultSet.getString(2));
        setSurname(resultSet.getString(3));
        setIsBlocked(resultSet.getBoolean(4));
        setRole(Role.valueOf(resultSet.getString(5)));
    }

    public void setStatementParams(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setBoolean(3, isBlocked);
        preparedStatement.setString(4, role.toString());
        preparedStatement.setLong(5, credentials.getId());
    }
}
