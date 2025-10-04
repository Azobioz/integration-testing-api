package com.azobioz.api.controller;

import com.azobioz.api.dto.ProductRequest;
import com.azobioz.api.dto.ProductResponse;
import com.azobioz.api.dto.UpdateUserResponse;
import com.azobioz.api.module.Product;
import com.azobioz.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts(@RequestParam(required = false) Double minPrice,
                                                             @RequestParam(required = false) Double maxPrice) {
        return ResponseEntity.ok(productService.getAllProducts(minPrice, maxPrice));
    }

    @PostMapping("/create")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest request) {
        productService.createProduct(request);
        Product product = productService.getProductByName(request.name());
        return ResponseEntity.ok(new ProductResponse(product.getId(), product.getName(), product.getPrice()));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id") Long id, @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }
}
