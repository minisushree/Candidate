package com.example.inventorymngt.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class OrderEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private OrderStatus status = OrderStatus.CREATED;
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	@Column(name = "product_id")
	private Long productId;
	@Column(name = "product_name", nullable = false, length = 255)
	private String productName;
	@Column(nullable = false)
	private Integer quantity;
	@Column(name = "unit_price", nullable = false, precision = 7, scale = 2)
	private BigDecimal unitPrice;
	@Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
	private BigDecimal totalAmount;
	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		if (status == null)
			status = OrderStatus.CREATED;
		createdAt = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long v) {
		id = v;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus v) {
		status = v;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer v) {
		userId = v;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long v) {
		productId = v;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String v) {
		productName = v;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer v) {
		quantity = v;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal v) {
		unitPrice = v;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal v) {
		totalAmount = v;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime v) {
		createdAt = v;
	}
}
