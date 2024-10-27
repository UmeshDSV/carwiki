package com.carwiki.carsearch.service;

import com.carwiki.carsearch.dto.VariantDto;

import java.util.List;

public interface VariantService {
    String addVariants(List<VariantDto> variantDtos);
}
