package com.jls.service;

import com.jls.model.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Created by jls on 02/03/17.
 */
public interface ReceiveHandler {

    void handleMessage(@Payload Message message, @Header("gatewayId") String gatewayId);
}
