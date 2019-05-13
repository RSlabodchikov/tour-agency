package com.netcracker.mano.touragency.entity;

import lombok.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@ToString(callSuper = true)
@NoArgsConstructor
@Builder
public class Booking extends BaseEntity {
    private long id;
    private int numberOfClients;
    private double totalPrice;
    private long userId;
    private long tourId;
    private long cardId;


    public void extractResult(ResultSet resultSet) throws SQLException {
        setId(resultSet.getLong(1));
        numberOfClients = resultSet.getByte(2);
        totalPrice = resultSet.getLong(3);
        userId = resultSet.getLong(4);
        tourId = resultSet.getLong(5);
        cardId = resultSet.getLong(6);
    }

    public void setStatementParams(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1, numberOfClients);
        preparedStatement.setDouble(2, totalPrice);
        preparedStatement.setLong(3, userId);
        preparedStatement.setLong(4, tourId);
        preparedStatement.setLong(5,cardId);
    }
}
