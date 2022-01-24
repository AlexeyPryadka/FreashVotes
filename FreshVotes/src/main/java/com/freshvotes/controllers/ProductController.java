package com.freshvotes.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.services.ProductService;

@Controller
public class ProductController {

	private ProductService productService;

	private Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public String getProducts(ModelMap model) {
		return "product";
	}

	@GetMapping("/products/{id}")
	public String getProduct(@PathVariable Long id, ModelMap model, HttpServletResponse response) throws IOException {
		Optional<Product> product = productService.findByIdWithUser(id);
		if (!product.isPresent()) {
			response.sendError(HttpStatus.NOT_FOUND.value(), "Product with id " + id + " was not found");
		} else {
			model.put("product", productService.findByIdWithUser(id));
		}

		return "product";
	}

	@GetMapping("/p/{name}")
	public String productUserView(@PathVariable String name, ModelMap model) {

		if (name != null) {
			try {
				String decodedProductName = URLDecoder.decode(name, StandardCharsets.UTF_8.name());
				Optional<Product> productOpt = productService.findByName(decodedProductName);

				if (productOpt.isPresent()) {
					model.put("product", productOpt.get());
				}
			} catch (UnsupportedEncodingException e) {
				logger.error("There was an error decoding a product URL", e);
			}
		}
		return "productUserView";
	}

	@PostMapping("/products")
	public String createProduct(@AuthenticationPrincipal User user) {
		Product product = productService.create(user);
		return "redirect:/products/" + product.getId();
	}

	@PostMapping("/products/{id}")
	public String saveProduct(@PathVariable Long id, Product product) {
		productService.update(product);
		return "redirect:/products/" + id;
	}

}
