package com.apap.tutorial7.repository;

import com.apap.tutorial7.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarDb extends JpaRepository<CarModel, Long> {
    CarModel findByType(String type);
}
