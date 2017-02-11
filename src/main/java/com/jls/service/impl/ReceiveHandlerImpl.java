package com.jls.service.impl;

import com.jls.model.Message;
import com.jls.service.InfluxDBService;
import com.jls.service.ReceiveHandler;
import org.influxdb.dto.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by jls on 02/03/17.
 */
@Service
public class ReceiveHandlerImpl implements ReceiveHandler {

    @Autowired
    PublishServiceImpl publishService;

    @Autowired
    InfluxDBService influxDBService;


    private static final Logger logger = LoggerFactory.getLogger(ReceiveHandlerImpl.class);

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.queue}", durable = "true"),
            exchange = @Exchange(value = "${spring.rabbitmq.exchange}", type = ExchangeTypes.HEADERS, durable = "true"),
            arguments = {
                    @Argument(name = "x-match", value = "all"),
                    @Argument(name = "gatewayId", value = "${spring.rabbitmq.gatewayId}")
            })
    )
    public void handleMessage(@Payload Message message, @Header("gatewayId") String gatewayId) {
        logger.info(new String(message.toString()));

        Point point = Point.measurement("dht22")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("gatewayId",gatewayId)
                .addField("temperature",message.getData().getTemperature())
                .addField("humidity", message.getData().getHumidity())
                .build();

        influxDBService.write(point);
        publishService.publish("/queue/DHT22",message.toString());
    }


}
