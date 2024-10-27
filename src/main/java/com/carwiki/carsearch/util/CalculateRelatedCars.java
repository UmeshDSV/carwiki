package com.carwiki.carsearch.util;

import com.carwiki.carsearch.model.RelatedCar;
import com.carwiki.carsearch.model.Variant;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CalculateRelatedCars {
    public static List<Variant> calculateRelatedCars(List<Variant> allVariants) {
        for(Variant variant: allVariants) {
            Map<Integer, RelatedCar> relatedCars = getRelatedCars(variant, allVariants);
            variant.setRelatedCars(relatedCars);
        }
        return allVariants;
    }
    public static Map<Integer, RelatedCar> getRelatedCars(Variant baseVariant, List<Variant> allVariants) {
        List<Variant> candidates = allVariants.stream()
                .filter(variant -> !variant.getId().equals(baseVariant.getId()) &&
                        Math.abs(variant.getPrice() - baseVariant.getPrice()) <= 300000)
                .collect(Collectors.toList());
        List<RelatedCar> rankedRelatedCars = candidates.stream()
                .map(candidate -> calculateSimilarityScoreAndSimilarFeatures(baseVariant, candidate))
                .sorted(Comparator.comparingInt(RelatedCar::getScore).reversed())
                .limit(10)
                .collect(Collectors.toList());


        Map<Integer, RelatedCar> relatedCars = new LinkedHashMap<>();
        for (int i = 0; i < rankedRelatedCars.size(); i++) {
            RelatedCar relatedCar = rankedRelatedCars.get(i);
            relatedCars.put(i + 1, relatedCar);
        }

        return relatedCars;
    }
    public static RelatedCar calculateSimilarityScoreAndSimilarFeatures(Variant baseVariant, Variant otherVariant) {
        AtomicInteger score = new AtomicInteger(0);
        List<Long> similarFeatureIds = new ArrayList<>();
        if (baseVariant.getTransmissionType() == otherVariant.getTransmissionType()) {
            score.addAndGet(5);
        }
        if (baseVariant.getFuelType() == otherVariant.getFuelType()) {
            score.addAndGet(4);
        }
        Map<Long, String> baseFeatureMap = baseVariant.getFeatures().stream()
                .collect(Collectors.toMap(
                        feature -> feature.getFeature().getId(),
                        feature -> feature.getValue()
                ));
        otherVariant.getFeatures().forEach(feature -> {
            Long featureId = feature.getFeature().getId();
            String featureValue = feature.getValue();

            if (baseFeatureMap.containsKey(featureId) && baseFeatureMap.get(featureId).equals(featureValue)) {
                similarFeatureIds.add(featureId);
                score.addAndGet(1);
            }
        });
        RelatedCar relatedCar = new RelatedCar();
        relatedCar.setVariantId(otherVariant.getId());
        relatedCar.setSimilarFeatureIds(similarFeatureIds);
        relatedCar.setScore(score.get());
        return relatedCar;
    }

}
