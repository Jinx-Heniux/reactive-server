package com.jls.service;

import org.influxdb.dto.Point;
import org.influxdb.dto.QueryResult;

/**
 * Created by jls on 02/03/17.
 */
public interface InfluxDBService {

    void write(Point point);

    QueryResult query(String queryString);
}
