package com.netcracker.mano.touragency.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Booking.class, name = "Booking"),
        @JsonSubTypes.Type(value = User.class, name = "User"),
        @JsonSubTypes.Type(value = Tour.class, name = "Tour"),
        @JsonSubTypes.Type(value = CreditCard.class, name = "CreditCard")}
)
public abstract class BaseEntity implements Serializable {
    protected long id;
    protected String type;
}
