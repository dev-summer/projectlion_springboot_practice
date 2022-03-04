package dev.summer.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!local")
public class RabbitMQ implements MessageQueueInterface{
    public static final Logger logger = LoggerFactory.getLogger(RabbitMQ.class);
    public RabbitMQ(){
        logger.info("rabbitmq component");
    }

    @Override
    public String readMessage() {
        // code for communicating with RabbitMQ
        return "message from RabbitMQ";
    }
}
