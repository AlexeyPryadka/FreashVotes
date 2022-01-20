package com.freshvotes.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.services.ProductService;

@Controller
public class DashboardController {
	
	private ProductService productService;
	
	@Autowired
	public DashboardController(ProductService productService) {
		this.productService = productService;
	}
	
	
	@GetMapping({"/","","/index"})
	public String indexView() {
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String dashboardView(ModelMap model, @AuthenticationPrincipal User user) {
		List<Product> products = productService.findAllByUser(user);
		Collections.sort(products);
		model.put("products", products);
		return "dashboard";
	}
	

}
