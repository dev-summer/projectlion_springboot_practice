package dev.summer.client.service;

import dev.summer.client.model.ActuatorLoggerDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class ActuatorService {
    // SpringBootActuator를 위한 서비스
    // api로 원격에 있는 서버의 actuator에 요청을 보내기 위한 용도로서 사용
    private static final Logger logger = LoggerFactory.getLogger(ActuatorService.class);
    private final WebClient actuatorClient;

    public ActuatorService(WebClient actuatorClient) {
        this.actuatorClient = actuatorClient;
    }

    public void setServerLogLevel(String loggerName, String logLevel){
        String uri = String.format("/loggers/%s", loggerName);
        ResponseEntity<?> bodiless = this.actuatorClient
                .post()
                .uri(uri)
                .bodyValue(new ActuatorLoggerDto(logLevel))
                // 여기까지는 요청을 준비하는 과정
                // 응답을 어떻게 처리할지에 대한 정의는 retrieve()부터
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
                    logger.error(clientResponse.statusCode().toString());
                    return Mono.empty(); // error가 아닌 것처럼 작동하게 하고 싶을 때 사용
                        })
                .onStatus(HttpStatus::is5xxServerError, clientResponse ->
                    Mono.error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR))
                )
                .toBodilessEntity() // Body = HTTP ResponseBody
                // body가 없다는 기준은 body를 실제로 안 보냈을수도 있지만, 바디가 와도 읽지 않겠다고 생각
                // 응답에서 바디를 제외한 나머지 부분(statusLine과 header들이 정의된 responseEntity에 응답을 준다
                .block(); // block()을 선언함으로써 위의 처리에 대한 요청이 완전히 이루어졌을 때 다음 줄로 넘어가라는 뜻으로 사용
    }

    public void shutdownServer(){
        String uri = "/shutdown";
        ResponseEntity<?> bodiless = this.actuatorClient
                .post()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, clientResponse ->
                        Mono.error(new ResponseStatusException(clientResponse.statusCode())))
                .toBodilessEntity()
                .block();
    }
}
