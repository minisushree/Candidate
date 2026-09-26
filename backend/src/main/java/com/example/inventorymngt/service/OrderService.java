package com.example.inventorymngt.service;

import com.example.inventorymngt.dto.CreateOrderRequest;
import com.example.inventorymngt.entity.*;
import com.example.inventorymngt.exception.BusinessException;
import com.example.inventorymngt.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {
	private final OrderRepo orderRepo;
	private final ProductRepo productRepo;

	public OrderService(OrderRepo orderRepo, ProductRepo productRepo) {
		this.orderRepo = orderRepo;
		this.productRepo = productRepo;
	}

	@Transactional
	public OrderEntity placeOrder(CreateOrderRequest request) {
		if (request.getUserId() == null || request.getUserId() <= 0)
			throw new BusinessException("User ID is required");
		if (request.getProductId() == null)
			throw new BusinessException("Product ID is required");
		if (request.getQuantity() == null || request.getQuantity() < 1 || request.getQuantity() > 999)
			throw new BusinessException("Quantity must be between 1 and 999");

		ProductEntitiy product = productRepo.findByIdForUpdate(request.getProductId())
				.orElseThrow(() -> new ResourceNotFoundException("Product not found: " + request.getProductId()));
		if (product.getStock() < request.getQuantity())
			throw new BusinessException(
					"Insufficient stock for product '" + product.getName() + "'. Available: " + product.getStock());

		BigDecimal total = product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())).setScale(2);
		if (total.compareTo(new BigDecimal("99999999.99")) > 0)
			throw new BusinessException("Order total exceeds the allowed maximum");

		product.setStock(product.getStock() - request.getQuantity());
		productRepo.save(product);

		OrderEntity order = new OrderEntity();
		order.setUserId(request.getUserId());
		order.setProductId(product.getId());
		order.setProductName(product.getName());
		order.setQuantity(request.getQuantity());
		order.setUnitPrice(product.getPrice().setScale(2));
		order.setTotalAmount(total);
		order.setStatus(OrderStatus.CREATED);
		return orderRepo.save(order);
	}

	public List<OrderEntity> getOrders(Integer userId) {
		return userId == null ? orderRepo.findAllByOrderByCreatedAtDesc()
				: orderRepo.findByUserIdOrderByCreatedAtDesc(userId);
	}

	public OrderEntity getOrder(Long id) {
		return orderRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));
	}

	@Transactional
	public OrderEntity cancelOrder(Long id) {
		OrderEntity order = getOrder(id);
		if (order.getStatus() != OrderStatus.CREATED)
			throw new BusinessException("Only CREATED orders can be cancelled");
		if (order.getProductId() != null) {
			productRepo.findByIdForUpdate(order.getProductId()).ifPresent(product -> {
				long restored = (long) product.getStock() + order.getQuantity();
				if (restored > 999999)
					throw new BusinessException("Restored stock would exceed 999999");
				product.setStock((int) restored);
				productRepo.save(product);
			});
		}
		order.setStatus(OrderStatus.CANCELLED);
		return orderRepo.save(order);
	}
}
