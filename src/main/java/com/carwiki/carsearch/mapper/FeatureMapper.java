package com.carwiki.carsearch.mapper;

import com.carwiki.carsearch.dto.FeatureDto;
import com.carwiki.carsearch.model.Feature;

public class FeatureMapper {
    public static Feature featureDtoToEntity(FeatureDto featureDto) {
        Feature feature = new Feature();
        feature.setId(featureDto.getId());
        feature.setName(featureDto.getName());
        return feature;
    }
    public static FeatureDto featureEntityToDto(Feature feature) {
        FeatureDto featureDto = new FeatureDto();
        featureDto.setId(feature.getId());
        featureDto.setName(feature.getName());
        return featureDto;
    }
}
