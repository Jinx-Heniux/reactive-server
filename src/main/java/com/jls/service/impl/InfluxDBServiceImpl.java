package com.jls.service.impl;

import com.jls.service.InfluxDBService;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.influxdb.InfluxDBTemplate;
import org.springframework.stereotype.Service;
/**
 * Created by jls on 02/03/17.
 */
@Service
public class InfluxDBServiceImpl implements InfluxDBService, InitializingBean {

    @Autowired
    InfluxDBTemplate<Point> influxDBTemplate;


    public void write(Point point) {
        influxDBTemplate.write(point);
    }

    public QueryResult query(String queryString) {
        Query query = new Query(queryString, influxDBTemplate.getDatabase());
        return influxDBTemplate.query(query);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        influxDBTemplate.createDatabase();
    }
}


