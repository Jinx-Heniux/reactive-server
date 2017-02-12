package com.jls.properties;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by jls on 01/03/17.
 */
@Data
@ConfigurationProperties("spring.rabbitmq")
public class RabbitMQProperties {

    @NotEmpty
    private String ipAddress;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
