package com.carwiki.carsearch.repository;

import com.carwiki.carsearch.model.CarDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CarDocumentRepository extends ElasticsearchRepository<CarDocument, Long> {
}
