package com.carwiki.carsearch.controller;

import com.carwiki.carsearch.dto.CarDto;
import com.carwiki.carsearch.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @PostMapping("/addCars")
    public ResponseEntity<String> addCar(@RequestBody List<CarDto> carDtos) {
        try {
            return new ResponseEntity<>(carService.addCars(carDtos), HttpStatus.CREATED);
        }catch (RuntimeException e) {
            return new ResponseEntity<>("Failed to add cars", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<CarDto> getCarbyId(@PathVariable Long id) {
        CarDto carDto = carService.getCarbyId(id);
        return ResponseEntity.ok(carDto);
    }
}
