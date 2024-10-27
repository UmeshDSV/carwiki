package com.carwiki.carsearch.service.impl;

import com.carwiki.carsearch.dto.VariantDto;
import com.carwiki.carsearch.mapper.CarMapper;
import com.carwiki.carsearch.model.Car;
import com.carwiki.carsearch.model.Variant;
import com.carwiki.carsearch.repository.CarRepository;
import com.carwiki.carsearch.repository.FeatureRepository;
import com.carwiki.carsearch.repository.VariantRepository;
import com.carwiki.carsearch.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VariantServiceImpl implements VariantService {
    @Autowired
    private VariantRepository variantRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private FeatureRepository featureRepository;
    @Override
    @Transactional
    public String addVariants(List<VariantDto> variantDtos) {
        List<Long> carIds = variantDtos.stream()
                .map(VariantDto::getCarId)
                .distinct()
                .collect(Collectors.toList());
        Map<Long, Car> carMap = carRepository.findAllById(carIds).stream()
                .collect(Collectors.toMap(Car::getId, car -> car));

        List<Variant> variants = variantDtos.stream()
                .map(variantDto -> {
                    Car car = carMap.get(variantDto.getCarId());
                    if (car == null) {
                        throw new RuntimeException("Car with ID " + variantDto.getCarId() + " not found");
                    }
                    return CarMapper.variantDtoToEntity(variantDto, car,featureRepository);
                })
                .collect(Collectors.toList());

        variantRepository.saveAll(variants);
        return "Successfully added variants";
    }
}
