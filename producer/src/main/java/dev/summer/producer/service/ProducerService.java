package dev.summer.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ProducerService {
    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    private final RabbitTemplate rabbitTemplate; // rabbitMQ에 요청을 주고받기 위한 객체 형태(인터페이스)
    private final Queue rabbitQueue; // 어

    public ProducerService(@Autowired RabbitTemplate rabbitTemplate,
                           @Autowired Queue rabbitQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitQueue = rabbitQueue;
    }

    AtomicInteger dots = new AtomicInteger(0);
    AtomicInteger count = new AtomicInteger(0);
    // AtomicInteger를 선언하는 이유: 메세지를 만듦에 있어서 서로 다른 스레드에서 접근할 수 있는 변수를 사용함으로써
    // 몇 개의 메세지가 지나갔는지를 파악하기 위해 (스레드가 꼬이는 상황 방지)

    public void send(){
        StringBuilder builder = new StringBuilder("Hello");
        if (dots.incrementAndGet() == 4) {
            dots.set(1);
        }
        builder.append(".".repeat(dots.get()));
        builder.append(count.incrementAndGet()); // count++ 과 같은 의미
        String message = builder.toString();

        rabbitTemplate.convertAndSend(rabbitQueue.getName());
        logger.info("Sent message: {}", message);
    }
}
