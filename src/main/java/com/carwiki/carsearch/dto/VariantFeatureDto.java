package com.carwiki.carsearch.dto;

import com.carwiki.carsearch.model.Variant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantFeatureDto implements Serializable {
    private Long id;
    private Long variantId;
    private Long featureId;
    private String featureName;
    private String value;
}
