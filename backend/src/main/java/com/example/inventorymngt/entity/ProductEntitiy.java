package com.example.inventorymngt.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class ProductEntitiy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 255)
	private String name;
	@Column(length = 2000)
	private String description;
	@Column(nullable = false, length = 50)
	private String category;
	@Column(nullable = false, precision = 7, scale = 2)
	private BigDecimal price;
	@Column(nullable = false)
	private Integer stock = 0;
	@Column(nullable = false)
	private boolean deleted = false;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		LocalDateTime now = LocalDateTime.now();
		createdAt = now;
		updatedAt = now;
		if (stock == null)
			stock = 0;
		if (price == null)
			price = BigDecimal.ZERO;
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer v) {
		stock = v;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean v) {
		deleted = v;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime v) {
		createdAt = v;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime v) {
		updatedAt = v;
	}
}
