package dev.summer.client.controller;

import dev.summer.client.service.ActuatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("actuator-api")
public class ActuatorController {
    private static final Logger logger = LoggerFactory.getLogger(ActuatorController.class);
    private final ActuatorService service;

    public ActuatorController(ActuatorService service) {
        this.service = service;
    }

    @PostMapping("logger/{loggername}")
    public ResponseEntity<?> setLogLevel(@PathVariable String loggerName,
                                         @RequestParam("log-level") String logLevel){
        this.service.setServerLogLevel(loggerName, logLevel);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("shutdown")
    public ResponseEntity<?> shutdownServer(){
        this.service.shutdownServer();
        return ResponseEntity.noContent().build();
    }
}
