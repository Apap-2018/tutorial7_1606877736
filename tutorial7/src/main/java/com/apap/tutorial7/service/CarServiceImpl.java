package com.apap.tutorial7.service;

import com.apap.tutorial7.model.CarModel;
import com.apap.tutorial7.repository.CarDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CarServiceImpl implements CarService {
    @Autowired
    private CarDb carDb;

    @Override
    public Optional<CarModel> getCarById(Long id) {
        return carDb.findById(id);
    }

    @Override
    public CarModel addCar(CarModel car) {
        carDb.save(car);
        return car;
    }

    @Override
    public void deleteCar(CarModel car) {
        carDb.delete(car);
    }

    @Override
    public List<CarModel> getAllCar(){ return carDb.findAll(); }
}
