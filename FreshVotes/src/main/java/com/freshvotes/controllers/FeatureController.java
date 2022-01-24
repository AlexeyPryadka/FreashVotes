package com.freshvotes.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freshvotes.domain.Feature;
import com.freshvotes.services.FeatureService;

@Controller
@RequestMapping("/products/{productId}/features")
public class FeatureController {

	Logger logger = LoggerFactory.getLogger(FeatureController.class);

	@Autowired
	private FeatureService featureService;

	@PostMapping("")
	public String create(@PathVariable Long id) {
		Feature feature = featureService.create(id);

		return "redirect:/products/" + id + "/features/" + feature.getId();
	}

	@GetMapping("{featureId}")
	public String getFeature(ModelMap model, @PathVariable Long id, @PathVariable Long featureId) {
		Optional<Feature> featureOpt = featureService.findById(featureId);
		if (featureOpt.isPresent()) {
			model.put("feature", featureOpt.get());
		}
		return "feature";
	}

	@PostMapping("{featureId}")
	public String updateFeature(Feature feature, @PathVariable Long id, @PathVariable Long featureId) {
		feature = featureService.save(feature);
		String encodedProductName;
		try {
			encodedProductName = URLEncoder.encode(feature.getProduct().getName(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.warn("Unable to encode the URL string: " + feature.getProduct().getName()
					+ ", redirecting to dashboard instead of the intended product user view page.");
			return "redirect:/dashboard";
		}

		return "redirect:/p/" + encodedProductName;
	}
}
