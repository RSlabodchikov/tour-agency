package com.netcracker.mano.touragency.entity;

import lombok.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ToString(callSuper = true )
@NoArgsConstructor
public class Booking extends BaseEntity {
    private int numberOfClients;
    private double totalPrice;
    private long userId;
    private long tourId;


    public void extractResult(ResultSet resultSet) throws SQLException {
        id = resultSet.getLong(1);
        numberOfClients = resultSet.getByte(2);
        totalPrice = resultSet.getLong(3);
        userId = resultSet.getLong(4);
        tourId = resultSet.getLong(5);
    }

    public void setStatementParams(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, numberOfClients);
        preparedStatement.setDouble(2, totalPrice);
    }
}
