package com.apap.tutorial7.service;

import com.apap.tutorial7.model.CarModel;

import java.util.List;
import java.util.Optional;

public interface CarService {
    Optional<CarModel> getCarById(Long id);

    CarModel addCar(CarModel car);

    void deleteCar(CarModel car);

    List<CarModel> getAllCar();
}
