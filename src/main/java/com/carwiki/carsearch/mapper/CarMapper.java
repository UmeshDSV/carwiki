package com.carwiki.carsearch.mapper;

import com.carwiki.carsearch.dto.CarDto;
import com.carwiki.carsearch.dto.RelatedCarDto;
import com.carwiki.carsearch.dto.VariantDto;
import com.carwiki.carsearch.dto.VariantFeatureDto;
import com.carwiki.carsearch.model.*;
import com.carwiki.carsearch.repository.FeatureRepository;
import com.carwiki.carsearch.repository.VariantRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarMapper {
    public static Car carDtoToEntity(CarDto carDto, FeatureRepository featureRepository) {
        Car car = new Car();
        car.setId(carDto.getId());
        car.setModel(carDto.getModel());
        car.setBrand(carDto.getBrand());
        car.setThumbnailUrl(carDto.getThumbnailUrl());
        if(carDto.getVariants() != null){
            List<Variant> variants = carDto.getVariants().stream()
                    .map(variantDto -> variantDtoToEntity(variantDto, car, featureRepository))
                    .collect(Collectors.toList());
            car.setVariants(variants);
        }
        return car;
    }

    public static CarDto carEntityToDto(Car car, VariantRepository variantRepository) {
        CarDto carDto = new CarDto();
        carDto.setId(car.getId());
        carDto.setBrand(car.getBrand());
        carDto.setModel(car.getModel());
        carDto.setThumbnailUrl(car.getThumbnailUrl());
        if(car.getVariants() != null){
            List<VariantDto> variantDtos = car.getVariants().stream()
                    .map(variant -> CarMapper.variantEntityToDto(variant,variantRepository))
                    .collect(Collectors.toList());
            carDto.setVariants(variantDtos);
        }
        return carDto;

    }
    public static Variant variantDtoToEntity(VariantDto variantDto, Car car, FeatureRepository featureRepository) {
        Variant variant = new Variant();
        variant.setId(variantDto.getId());
        variant.setPrice(variantDto.getPrice());
        variant.setCar(car);
        variant.setName(variantDto.getName());
        variant.setFuelType(variantDto.getFuelType());
        variant.setTransmissionType(variantDto.getTransmissionType());
        variant.setYearOfManufacture(variantDto.getYearOfManufacture());
//        if (variantDto.getRelatedCars() != null) {
//            Map<Integer, RelatedCar> relatedCars = variantDto.getRelatedCars().entrySet().stream()
//                    .collect(Collectors.toMap(Map.Entry::getKey,
//                            entry -> new RelatedCar(entry.getValue().getVariantId())));
//            variant.setRelatedCars(relatedCars);
//        }
        if (variantDto.getFeatures() != null){
            List<VariantFeature> variantFeatures = variantDto.getFeatures().stream()
                    .map(variantFeatureDto -> variantFeatureDtoToEntity(variantFeatureDto, variant, featureRepository))
                    .collect(Collectors.toList());
            variant.setFeatures(variantFeatures);
        }
        return  variant;
    }
    public static VariantDto variantEntityToDto(Variant variant, VariantRepository variantRepository) {
        VariantDto variantDto = new VariantDto();
        variantDto.setId(variant.getId());
        variantDto.setYearOfManufacture(variant.getYearOfManufacture());
        variantDto.setName(variant.getName());
        variantDto.setPrice(variant.getPrice());
        variantDto.setFuelType(variant.getFuelType());
        variantDto.setTransmissionType(variant.getTransmissionType());
        if (variant.getRelatedCars() != null) {
            List<Long> relatedVariantIds = variant.getRelatedCars().values().stream()
                    .map(RelatedCar::getVariantId)
                    .distinct()
                    .collect(Collectors.toList());

            Map<Long, Variant> variantMap = variantRepository.findAllById(relatedVariantIds).stream()
                    .collect(Collectors.toMap(Variant::getId, v -> v));

            Map<Integer, RelatedCarDto> relatedCarDTOs = variant.getRelatedCars().entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> {
                                RelatedCar relatedCar = entry.getValue();
                                RelatedCarDto dto = new RelatedCarDto();

                                dto.setVariantId(relatedCar.getVariantId());
                                dto.setSimilarFeatureIds(relatedCar.getSimilarFeatureIds());

                                Variant relatedVariant = variantMap.get(relatedCar.getVariantId());
                                if (relatedVariant != null) {
                                    dto.setVariantName(relatedVariant.getName());
                                    dto.setVariantPrice(relatedVariant.getPrice());
                                    dto.setCarBrand(relatedVariant.getCar().getBrand());
                                    dto.setCarModel(relatedVariant.getCar().getModel());
                                    List<VariantFeatureDto> variantFeatureDtos = relatedVariant.getFeatures().stream()
                                            .map(CarMapper::variantFeatureEntityToDto)  // Assuming this method exists in CarMapper
                                            .collect(Collectors.toList());

                                    dto.setVariantFeatures(variantFeatureDtos);
                                }

                                return dto;
                            }
                    ));
            variantDto.setRelatedCars(relatedCarDTOs);
        }
        if (variant.getFeatures() != null) {
            List<VariantFeatureDto> variantFeatureDtos = variant.getFeatures().stream()
                    .map(CarMapper::variantFeatureEntityToDto)
                    .collect(Collectors.toList());
            variantDto.setFeatures(variantFeatureDtos);
        }
        return variantDto;
    }

    public static VariantFeature variantFeatureDtoToEntity(VariantFeatureDto variantFeatureDto, Variant variant, FeatureRepository featureRepository) {
        VariantFeature variantFeature = new VariantFeature();
        variantFeature.setVariant(variant);
        variantFeature.setFeature(featureRepository.findById(variantFeatureDto.getFeatureId()).orElseThrow(() -> new IllegalArgumentException("Feature not found with id: " + variantFeatureDto.getFeatureId())));
        variantFeature.setValue(variantFeatureDto.getValue());
        variantFeature.setId(variantFeatureDto.getId());
        return variantFeature;
    }
    public static VariantFeatureDto variantFeatureEntityToDto(VariantFeature variantFeature) {
        VariantFeatureDto variantFeatureDto = new VariantFeatureDto();
        variantFeatureDto.setFeatureName(variantFeature.getFeature().getName());
        variantFeatureDto.setFeatureId(variantFeature.getFeature().getId());
        variantFeatureDto.setValue(variantFeature.getValue());
        variantFeatureDto.setVariantId(variantFeature.getVariant().getId());
        variantFeatureDto.setId(variantFeature.getId());
        return variantFeatureDto;
    }

}
