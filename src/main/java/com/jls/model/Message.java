package com.jls.model;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by jls on 02/03/17.
 */
@Data
public class Message implements Serializable {

    private long expire;
    private long created;
    private DHT22Sensor data;
}
