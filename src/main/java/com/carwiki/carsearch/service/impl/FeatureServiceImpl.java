package com.carwiki.carsearch.service.impl;

import com.carwiki.carsearch.dto.FeatureDto;
import com.carwiki.carsearch.mapper.FeatureMapper;
import com.carwiki.carsearch.model.Feature;
import com.carwiki.carsearch.repository.FeatureRepository;
import com.carwiki.carsearch.service.FeatureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeatureServiceImpl implements FeatureService {
    private static final Logger logger = LoggerFactory.getLogger(FeatureServiceImpl.class);
    @Autowired
    private FeatureRepository featureRepository;
    @Override
    public String addFeatureDefinition(List<FeatureDto> featureDtos) {
        List<Feature> features = featureDtos.stream()
                        .map(FeatureMapper::featureDtoToEntity)
                                .collect(Collectors.toList());
        featureRepository.saveAll(features);
        logger.info("Successfully saved Features");
        return "successfully saved feature definition";
    }

    @Override
    public List<FeatureDto> getAllFeatures() {
        List<Feature> features = featureRepository.findAll();
        return features.stream()
                .map(FeatureMapper::featureEntityToDto)
                .collect(Collectors.toList());
    }
}
