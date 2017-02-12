package com.jls;

import com.jls.config.InfluxDBConfig;
import com.jls.config.JacksonConfig;
import com.jls.config.RabbitMQConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@Import({JacksonConfig.class,
		InfluxDBConfig.class,
		RabbitMQConfig.class})
public class ReactiveServerApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveServerApplication.class, args);
	}
}
