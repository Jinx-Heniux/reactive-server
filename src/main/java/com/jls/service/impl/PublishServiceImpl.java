package com.jls.service.impl;

import com.jls.service.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by jls on 02/03/17.
 */
@Service
public class PublishServiceImpl implements PublishService {

    @Autowired
    SimpMessagingTemplate messagingTemplate;

    public void publish(String destination, Object payload) {
        messagingTemplate.convertAndSend(destination, payload);
    }

}
