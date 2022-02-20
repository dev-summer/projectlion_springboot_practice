package com.company;

import java.util.ArrayList;
import java.util.List;

public class Road {
    private List<CarInterface> carsOnRoad;

    public Road() {
        this.carsOnRoad = new ArrayList<>();
    }

    public Road(List<CarInterface> carsOnRoad) {
        this.carsOnRoad = carsOnRoad;
    }

    public void addCar(CarInterface car){
        this.carsOnRoad.add(car);
    }
}
