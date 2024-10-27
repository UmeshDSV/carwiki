package com.carwiki.carsearch.repository;

import com.carwiki.carsearch.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {

}
