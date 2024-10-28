package com.carwiki.carsearch.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "car")
public class CarDocument {
    @Id
    private Long id;
    private String brand;
    private String model;
}
