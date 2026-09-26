package com.example.inventorymngt.dto;

import java.math.BigDecimal;

public class StoreProductResponse {
	private Long id;
	private String name;
	private String description;
	private String category;
	private BigDecimal price;
	private boolean inStock;

	public Long getId() {
		return id;
	}

	public void setId(Long v) {
		id = v;
	}

	public String getName() {
		return name;
	}

	public void setName(String v) {
		name = v;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String v) {
		description = v;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String v) {
		category = v;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal v) {
		price = v;
	}

	public boolean isInStock() {
		return inStock;
	}

	public void setInStock(boolean v) {
		inStock = v;
	}
}
