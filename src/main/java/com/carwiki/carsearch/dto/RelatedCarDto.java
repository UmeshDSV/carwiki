package com.carwiki.carsearch.dto;

import com.carwiki.carsearch.model.VariantFeature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelatedCarDto implements Serializable {
    private Long variantId;
    private List<Long> similarFeatureIds;
    private String variantName;
    private Long variantPrice;
    private String carBrand;
    private String carModel;
    private List<VariantFeatureDto> variantFeatures;
}
