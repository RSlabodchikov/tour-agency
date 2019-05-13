package com.netcracker.mano.touragency.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.netcracker.mano.touragency.locale.date.format.LocalDateDeserializer;
import com.netcracker.mano.touragency.locale.date.format.LocalDateSerializer;
import lombok.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@Builder
public class Tour extends BaseEntity {
    private long id;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate evictionDate;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate settlementDate;
    private String country;
    private Category category;
    private Double price;
    private String description;
    private int numberOfClients;


    public void extractResult(ResultSet resultSet) throws SQLException {
        setId(resultSet.getLong(1));
        setSettlementDate(resultSet.getDate(2).toLocalDate());
        setEvictionDate(resultSet.getDate(3).toLocalDate());
        setCountry(resultSet.getString(4));
        setPrice(resultSet.getDouble(5));
        setNumberOfClients(resultSet.getByte(6));
        setDescription(resultSet.getString(7));
        setCategory(Category.valueOf(resultSet.getString(10)));
    }

    public void setStatementParamsToChange(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setDouble(1, price);
        preparedStatement.setInt(2, numberOfClients);
        preparedStatement.setLong(3, getId());
    }

    public void setStatementParamsToCreate(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setDate(1, Date.valueOf(settlementDate));
        preparedStatement.setDate(2, Date.valueOf(evictionDate));
        preparedStatement.setString(3, country);
        preparedStatement.setDouble(4, price);
        preparedStatement.setInt(5, numberOfClients);
        preparedStatement.setString(6, description);
    }
}
