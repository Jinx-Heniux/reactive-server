package com.jls.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jls.common.jackson.ZonedDateTimeSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Created by jls on 11/01/17.
 */
@Data
public class DHT22Sensor extends Sensor implements Serializable {

    @JsonSerialize(using = ZonedDateTimeSerializer.class)
    private ZonedDateTime time;
    private String gatewayId;
    private Number temperature;
    private Number humidity;

    @Override
    public String toString() {
        return "DHT22Sensor{" +
                "temperature='" + temperature + '\'' +
                ", humidity=" + humidity +
                '}';
    }
}

