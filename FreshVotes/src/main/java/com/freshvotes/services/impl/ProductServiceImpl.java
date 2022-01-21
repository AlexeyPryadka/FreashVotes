package com.freshvotes.services.impl;


import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;
import com.freshvotes.repositories.ProductRepository;
import com.freshvotes.services.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
	
	private ProductRepository productRepository;
	
	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}
	public Product create(User user) {
		Product product = new Product();

		product.setPublished(false);
		product.setUser(user);

		return productRepository.save(product);
	}
	
	
	public List<Product> findAllByUser(User user) {
		return productRepository.findByUser(user);
	}
	
	public Optional<Product> findByIdWithUser(Long id) {
		 return productRepository.findByIdWithUser(id);
		
	}
	
	public Product update(Product update) {
		Product product = findByIdWithUser(update.getId()).get();
		product.setName(update.getName());
		product.setPublished(update.getPublished());
		return productRepository.save(product);
		
	}
	

}
