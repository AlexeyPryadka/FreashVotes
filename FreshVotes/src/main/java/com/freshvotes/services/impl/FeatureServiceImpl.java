package com.freshvotes.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.Product;
import com.freshvotes.repositories.FeatureRepository;
import com.freshvotes.repositories.ProductRepository;
import com.freshvotes.services.FeatureService;

@Service
public class FeatureServiceImpl implements FeatureService {

	private FeatureRepository featureRepository;
	private ProductRepository productRepository;

	@Autowired
	public FeatureServiceImpl(FeatureRepository featureRepository, ProductRepository productRepository) {
		this.featureRepository = featureRepository;
		this.productRepository = productRepository;
	}

	@Override
	public Feature create(Long productId) {
		Feature feature = new Feature();
		Optional<Product> productOpt = productRepository.findById(productId);

		if (productOpt.isPresent()) {
			Product product = productOpt.get();

			feature.setProduct(product);
			product.getFeatures().add(feature);

			feature.setStatus("Pending review");

			return featureRepository.save(feature);

		}

		return feature;
	}

	@Override
	public Optional<Feature> findById(Long featureId) {
		return featureRepository.findById(featureId);
	}

	@Override
	public Feature save(Feature feature) {
		return featureRepository.save(feature);
	}

}
