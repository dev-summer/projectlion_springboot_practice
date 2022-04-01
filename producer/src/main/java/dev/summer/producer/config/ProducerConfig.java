package dev.summer.producer.config;

import com.google.gson.Gson;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {

    @Bean
    public Queue queue(){
        return new Queue("boot.amqp.worker-queue", true, false, true);
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }
    // gson을 제공하기 위한 bean
}
