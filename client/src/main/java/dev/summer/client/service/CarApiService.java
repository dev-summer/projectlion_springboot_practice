package dev.summer.client.service;

import dev.summer.client.model.CarDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class CarApiService {
    private static final Logger logger = LoggerFactory.getLogger(CarApiService.class);
    private final WebClient randomDataClient;

    public CarApiService(WebClient randomDataClient) {
        this.randomDataClient = randomDataClient;
    }

    public CarDto buyNewCar(){
        CarDto result = this.randomDataClient
                .get()
                .uri("/api/vehicle/random_vehicle")
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                        Mono.empty())
                .bodyToMono(CarDto.class) // Mono: reactive programming에서 사용하는 객체의 단위 중 하나
                // 바디를 어떤 자바클래스로 해석할지를 전달할 수 있음
                // 응답에 대한 바디를 ()안에 들어간 형태로 응답하고자 노력하게 됨
                .block();

        return result;
    }
}
