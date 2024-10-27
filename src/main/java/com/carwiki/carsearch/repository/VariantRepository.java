package com.carwiki.carsearch.repository;

import com.carwiki.carsearch.model.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantRepository extends JpaRepository<Variant, Long> {
}
