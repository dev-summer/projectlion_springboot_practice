package dev.summer.client.controller;

import dev.summer.client.model.CarDto;
import dev.summer.client.service.CarApiService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("random-data")
public class CarApiController {
    private final CarApiService service;

    public CarApiController(CarApiService service) {
        this.service = service;
    }

    @PostMapping("buy-car")
    public CarDto buyCar(){
        return this.service.buyNewCar();
    }

//    @GetMapping("show-cars")
//    public List<CarDto> getCars(){
//        return this.service.getCarsOwned();
//    }
}
