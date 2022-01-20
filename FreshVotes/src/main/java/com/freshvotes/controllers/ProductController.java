package com.freshvotes.controllers;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

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
		Optional<Product> productOpt = productService.findByIdWithUser(id);

		if (productOpt.isPresent()) {
			model.put("product", productOpt.get());
		} else {
			response.sendError(HttpStatus.NOT_FOUND.value(), "Product with id " + id + " was not found");
		}

		return "product";
	}

	@PostMapping("/products")
	public String createProduct(@AuthenticationPrincipal User user) {
		Product product = productService.create(user);
		return "redirect:/products/" + product.getId();
	}

	@PostMapping("/products/{id}")
	public String saveProduct(@PathVariable Long id,Product product) {
		productService.update(product);
		return "redirect:/products/" + id;
	}

}
