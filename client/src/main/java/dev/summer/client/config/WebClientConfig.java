package dev.summer.client.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("{ncp.api.access-key:stub-api-key}")
    private String accessKey;

    @Bean
    public WebClient defaultWebClient(){
        return WebClient.create();
    }

    @Bean
    public WebClient actuatorClient(){
        return WebClient.builder()
                .baseUrl("http://localhost:8081/actuator") // 보내고자 하는 요청들의 기본이 되는 url. 해당 bean에서 반환되는 WebClient는 모든 요청이 해당 경로에서 시작되게 됨
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // 이 웹클라이언트로 보내는 요청들은 모두 해당 헤더값이 포함되어 있을 것이다.
                .build();
    }

    @Bean
    public WebClient ncpWebClient(){
        return WebClient.builder()
                .defaultHeader("x-ncp-iam-access-key", accessKey)
                .build();
    }

    @Bean
    public WebClient randomDataClient(ObjectMapper baseConfig){
        ObjectMapper newMapper = baseConfig.copy(); // 기본값 설정 복사
        newMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        // 전략을 webclient에 전달하기 위한 객체
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer ->
                        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(newMapper)))
                .build();

        return WebClient.builder()
                .baseUrl("https://random-data-api.com")
                .exchangeStrategies(exchangeStrategies)
                .build();
    }
}
