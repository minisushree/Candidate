package com.example.inventorymngt.dto;

public class CreateOrderRequest {
	private Integer userId;
	private Long productId;
	private Integer quantity;

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

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer v) {
		quantity = v;
	}
}
