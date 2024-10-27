package com.carwiki.carsearch.service.impl;

import com.carwiki.carsearch.dto.CarDto;
import com.carwiki.carsearch.exception.CarCreationException;
import com.carwiki.carsearch.mapper.CarMapper;
import com.carwiki.carsearch.model.Car;
import com.carwiki.carsearch.model.Variant;
import com.carwiki.carsearch.repository.CarRepository;
import com.carwiki.carsearch.repository.FeatureRepository;
import com.carwiki.carsearch.repository.VariantRepository;
import com.carwiki.carsearch.service.CarService;
import com.carwiki.carsearch.util.CalculateRelatedCars;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private static final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private VariantRepository variantRepository;

    /**
     * Adds a list of cars and their associated variants, calculates related cars,
     * and saves them to the database. Logs each step with detailed information.
     */
    @Override
    @Transactional
    public String addCars(List<CarDto> carDtos) {
        logger.info("Starting addCars process for {} cars", carDtos.size());

        try {
            // Map DTOs to entities and log each mapped car entity
            List<Car> cars = carDtos.stream()
                    .map(carDto -> CarMapper.carDtoToEntity(carDto, featureRepository))
                    .collect(Collectors.toList());

            cars.forEach(car -> logger.debug("Mapped CarDto to Car entity: {}", car));

            // Save all cars
            carRepository.saveAll(cars);
            logger.info("Successfully saved {} cars to the database.", cars.size());

            // Fetch all variants to calculate related cars
            List<Variant> allVariants = variantRepository.findAll();
            logger.debug("Fetched {} variants for related cars calculation.", allVariants.size());

            // Calculate related cars and save updated variants
            CalculateRelatedCars.calculateRelatedCars(allVariants);
            variantRepository.saveAll(allVariants);

            logger.info("Successfully calculated and saved related cars for all variants.");
            return "Successfully added the cars.";

        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation while adding cars: {}. Cause: {}", carDtos, e.getMessage(), e);
            throw new CarCreationException("Failed to add cars due to data integrity violation.", e);

        } catch (Exception e) {
            logger.error("Unexpected error occurred while adding cars: {}. Cause: {}", carDtos, e.getMessage(), e);
            throw new CarCreationException("An unexpected error occurred while adding the cars. Please try again later.", e);
        }
    }

    /**
     * Retrieves a car by its ID and caches the result. Logs details at each step.
     */
    @Override
    @Cacheable(value = "car", key = "#id")
    public CarDto getCarbyId(Long id) {
        logger.info("Retrieving car with ID: {}", id);

        try {
            // Fetch car by ID and map to DTO
            Car car = carRepository.findById(id)
                    .orElseThrow(() -> {
                        logger.warn("Car with ID {} not found in the database.", id);
                        return new RuntimeException("Car does not exist");
                    });

            CarDto carDto = CarMapper.carEntityToDto(car, variantRepository);
            logger.debug("Mapped Car entity to CarDto: {}", carDto);

            logger.info("Successfully retrieved and mapped car with ID: {}", id);
            return carDto;

        } catch (Exception e) {
            logger.error("Error occurred while retrieving car with ID {}. Cause: {}", id, e.getMessage(), e);
            throw e;
        }
    }
}
