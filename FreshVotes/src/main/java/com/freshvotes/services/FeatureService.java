package com.freshvotes.services;

import java.util.Optional;

import com.freshvotes.domain.Feature;

public interface FeatureService {

	Feature create(Long productId);

	Optional<Feature> findById(Long featureId);

	Feature save(Feature feature);
}
