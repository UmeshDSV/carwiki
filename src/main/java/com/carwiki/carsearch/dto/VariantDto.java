package com.carwiki.carsearch.dto;

import com.carwiki.carsearch.enums.FuelType;
import com.carwiki.carsearch.enums.TransmissionType;
import com.carwiki.carsearch.model.Car;
import com.carwiki.carsearch.model.VariantFeature;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VariantDto implements Serializable {
    private Long id;
    private Long carId;
    private TransmissionType transmissionType;
    private FuelType fuelType;
    private Long price;
    private String name;
    private Integer yearOfManufacture;
    private Map<Integer, RelatedCarDto> relatedCars;
    private List<VariantFeatureDto> features;
}
