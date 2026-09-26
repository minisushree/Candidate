package com.example.inventorymngt.service;

import com.example.inventorymngt.entity.ProductEntitiy;
import com.example.inventorymngt.entity.ProductRepo;
import com.example.inventorymngt.exception.BusinessException;
import com.example.inventorymngt.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class ProductService {
	private static final Set<String> CATEGORIES = Set.of("Electronics", "Clothing", "Home & Garden", "Sports", "Books",
			"Other");
	private final ProductRepo productRepo;

	public ProductService(ProductRepo productRepo) {
		this.productRepo = productRepo;
	}

	public List<ProductEntitiy> getProducts() {
		return productRepo.findByDeletedFalseOrderByIdAsc();
	}

	public ProductEntitiy getProduct(Long id) {
		return productRepo.findByIdAndDeletedFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found: " + id));
	}

	public ProductEntitiy addProduct(ProductEntitiy product) {
		validate(product);
		product.setId(null);
		product.setStock(0);
		product.setDeleted(false);
		return productRepo.save(product);
	}

	public ProductEntitiy updateProduct(Long id, ProductEntitiy payload) {
		ProductEntitiy existing = getProduct(id);
		validate(payload);
		existing.setName(payload.getName());
		existing.setDescription(payload.getDescription());
		existing.setCategory(payload.getCategory());
		existing.setPrice(payload.getPrice());
		return productRepo.save(existing);
	}

	public void deleteProduct(Long id) {
		ProductEntitiy product = getProduct(id);
		product.setDeleted(true);
		productRepo.save(product);
	}

	public ProductEntitiy adjustStock(Long id, Integer amount) {
		if (amount == null || amount == 0)
			throw new BusinessException("Stock adjustment amount must not be zero");
		ProductEntitiy product = getProduct(id);
		long newStock = (long) product.getStock() + amount;
		if (newStock < 0)
			throw new BusinessException("Stock cannot be reduced below zero");
		if (newStock > 999999)
			throw new BusinessException("Stock cannot exceed 999999");
		product.setStock((int) newStock);
		return productRepo.save(product);
	}

	private void validate(ProductEntitiy p) {
		if (p.getName() == null || p.getName().trim().isEmpty() || p.getName().length() > 255)
			throw new BusinessException("Name is required and must be 1-255 characters");
		if (p.getDescription() != null && p.getDescription().length() > 2000)
			throw new BusinessException("Description must be at most 2000 characters");
		if (p.getCategory() == null || !CATEGORIES.contains(p.getCategory()))
			throw new BusinessException(
					"Category must be one of: Electronics, Clothing, Home & Garden, Sports, Books, Other");
		BigDecimal price = p.getPrice();
		if (price == null || price.compareTo(BigDecimal.ZERO) < 0 || price.compareTo(new BigDecimal("99999.99")) > 0)
			throw new BusinessException("Price must be between 0.00 and 99999.99");
	}
}
