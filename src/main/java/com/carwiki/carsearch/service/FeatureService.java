package com.carwiki.carsearch.service;

import com.carwiki.carsearch.dto.FeatureDto;

import java.util.List;

public interface FeatureService {
    String addFeatureDefinition(List<FeatureDto> featureDtos);
    List<FeatureDto> getAllFeatures();
}
