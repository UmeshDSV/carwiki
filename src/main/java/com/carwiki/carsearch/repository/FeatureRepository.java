package com.carwiki.carsearch.repository;

import com.carwiki.carsearch.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
}
