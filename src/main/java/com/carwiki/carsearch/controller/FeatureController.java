package com.carwiki.carsearch.controller;

import com.carwiki.carsearch.dto.FeatureDto;
import com.carwiki.carsearch.model.Feature;
import com.carwiki.carsearch.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/features")
public class FeatureController {
    @Autowired
    private FeatureService featureService;

    @PostMapping("/addFeature")
    public ResponseEntity<String> addFeatureDefinition(@RequestBody List<FeatureDto> featureDtos) {
        return new ResponseEntity<>(featureService.addFeatureDefinition(featureDtos), HttpStatus.CREATED);
    }

    @GetMapping("/getAllFeatures")
    public ResponseEntity<List<FeatureDto>> getAllFeatures(){
        return ResponseEntity.ok(featureService.getAllFeatures());
    }
}
