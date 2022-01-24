package com.freshvotes.services;

import java.util.List;
import java.util.Optional;

import com.freshvotes.domain.Product;
import com.freshvotes.domain.User;

public interface ProductService {
	Product create(User user);

	Product update(Product update);

	List<Product> findAllByUser(User user);

	Optional<Product> findByIdWithUser(Long id);

	Optional<Product> findByName(String name);
}
