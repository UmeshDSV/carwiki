package com.carwiki.carsearch.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto implements Serializable {
    private Long id;
    private String brand;
    private String model;
    private String thumbnailUrl;
    private List<VariantDto> variants;
}
