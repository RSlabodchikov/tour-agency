package com.netcracker.mano.touragency.entity;

import lombok.*;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CreditCard extends BaseEntity {
    private BigInteger number;
    private double balance;
    private long userId;




    public void extractResult(ResultSet resultSet) throws SQLException {
        id = resultSet.getLong(1);
        number = new BigInteger(resultSet.getString(2));
        balance = resultSet.getDouble(3);
        userId = resultSet.getLong(4);
    }

    public void setStatementParams(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, number.toString());
        preparedStatement.setDouble(2, balance);
        preparedStatement.setLong(3, userId);
    }
}
