package com.example.inventorymngt.Controllers;

import com.example.inventorymngt.dto.StoreProductResponse;
import com.example.inventorymngt.entity.ProductEntitiy;
import com.example.inventorymngt.service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/store/products")
public class StoreProductController {
	private final ProductService productService;

	public StoreProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping
	public List<StoreProductResponse> list() {
		return productService.getProducts().stream().map(this::toResponse).toList();
	}

	@GetMapping("/{id}")
	public StoreProductResponse get(@PathVariable Long id) {
		return toResponse(productService.getProduct(id));
	}

	private StoreProductResponse toResponse(ProductEntitiy p) {
		StoreProductResponse r = new StoreProductResponse();
		r.setId(p.getId());
		r.setName(p.getName());
		r.setDescription(p.getDescription());
		r.setCategory(p.getCategory());
		r.setPrice(p.getPrice());
		r.setInStock(p.getStock() > 0);
		return r;
	}
}
