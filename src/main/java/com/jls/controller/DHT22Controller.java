package com.jls.controller;

import ca.krasnay.sqlbuilder.SelectBuilder;
import com.jls.model.DHT22Sensor;
import com.jls.model.ListResource;
import com.jls.service.InfluxDBService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.influxdb.dto.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;


@RestController
@RequestMapping("/api/v1/sensor/dht22")
@Api(value = "dht22", description = "DHT22 Sensor data")
public class DHT22Controller {

    @Autowired
    InfluxDBService influxDBService;

    private ListResource<Map> toListResource(QueryResult queryResult) {
        ListResource<Map> listResource = new ListResource<>();
        if(queryResult.getResults() != null && queryResult.getResults().size() > 0) {
            QueryResult.Result result = queryResult.getResults().get(0);
            if(result != null && result.getSeries() != null && result.getSeries().size() > 0) {
                QueryResult.Series series = result.getSeries().get(0);
                series.getValues().forEach(value -> {
                    Map<String,Object> mappedValues = new HashMap<>();
                    IntStream.range(0,  value.size())
                            .forEach(index -> {
                                mappedValues.put(series.getColumns().get(index), value.get(index));
                            });
                    listResource.getItems().add(mappedValues);
                });
            }
        }
        return listResource;
    }

    @SneakyThrows
    private DHT22Sensor toTransferObject(QueryResult queryResult) {
        DHT22Sensor dht22Sensor = new DHT22Sensor();
        QueryResult.Series series = queryResult.getResults().get(0).getSeries().get(0);
        dht22Sensor.setTime(ZonedDateTime.parse((String) series.getValues().get(0).get(0)));
        dht22Sensor.setGatewayId((String) series.getValues().get(0).get(1));
        dht22Sensor.setHumidity((Double) series.getValues().get(0).get(2));
        dht22Sensor.setTemperature((Double) series.getValues().get(0).get(3));
        return dht22Sensor;
    }

    //http://localhost:8084//api/v1/sensor/dht22/fresh/raspberry_1
    @RequestMapping(value = "/fresh/{gatewayId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get latest fresh data", notes = "Get latest fresh data")
    public ResponseEntity<DHT22Sensor> getFreshSensorData(@PathVariable("gatewayId") String gatewayId) {

        String getLastValueQuery =
                new SelectBuilder()
                        .column("*")
                        .from("dht22")
                        .where("gatewayId = '" + gatewayId + "'")
                        .orderBy("time", false)
                        .toString();
        getLastValueQuery = getLastValueQuery.concat(" LIMIT 1");

        return new ResponseEntity<>(toTransferObject(influxDBService.query(getLastValueQuery)),  HttpStatus.OK);
    }
    //http://localhost:8084/api/v1/sensor/dht22/continuous/raspberry_1?sample=1h&range=12h
    @RequestMapping(value = "/continuous/{gatewayId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Continuous Data", notes = "Get Continuous Data")
    public ResponseEntity<ListResource<Map>> getContinuousData(@PathVariable("gatewayId") String gatewayId,
                                                               @RequestParam(value = "sample", required = true) String sample,
                                                               @RequestParam(value = "range", required = true) String range) {
        String getContinuousQuey =
            new SelectBuilder()
                    .column("*")
                    .from("cq_dht22_" + sample)
                    .where("gatewayId = '" + gatewayId + "' AND time >= now() - " + range)
                    .orderBy("time", false)
                    .toString();

        return new ResponseEntity<>(toListResource(influxDBService.query(getContinuousQuey)), HttpStatus.OK);
    }

}
