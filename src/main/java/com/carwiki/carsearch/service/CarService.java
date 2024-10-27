package com.carwiki.carsearch.service;

import com.carwiki.carsearch.dto.CarDto;

import java.util.List;

public interface CarService {
    String addCars(List<CarDto> carDto);
    CarDto getCarbyId(Long id);
}
