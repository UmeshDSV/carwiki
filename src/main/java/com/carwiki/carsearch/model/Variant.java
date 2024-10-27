package com.carwiki.carsearch.model;

import com.carwiki.carsearch.enums.FuelType;
import com.carwiki.carsearch.enums.TransmissionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "variant")
@Entity
public class Variant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transmission_type")
    private TransmissionType transmissionType;
    @Column(name = "fuel_type")
    private FuelType fuelType;
    private Long price;
    @Column(name = "name", unique = true, nullable = false)
    private String name;
    @Column(name="year_of_manufacture")
    private Integer yearOfManufacture;
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name="related_cars", columnDefinition = "json")
    private Map<Integer, RelatedCar> relatedCars;
    @ManyToOne
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    private Car car;
    @OneToMany(mappedBy = "variant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VariantFeature> features;
}
