package com.carwiki.carsearch.controller;

import com.carwiki.carsearch.dto.VariantDto;
import com.carwiki.carsearch.service.VariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/variants")
public class VariantController {
    @Autowired
    private VariantService variantService;
    public ResponseEntity<String> addVariants(List<VariantDto> variantDtos) {
        return new ResponseEntity<>(variantService.addVariants(variantDtos), HttpStatus.CREATED);
    }
}
