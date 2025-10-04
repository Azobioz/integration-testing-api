package com.azobioz.api.service;

import com.azobioz.api.dto.ProductRequest;
import com.azobioz.api.dto.ProductResponse;
import com.azobioz.api.module.Product;
import com.azobioz.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public List<ProductResponse> getAllProducts(Double minPrice, Double maxPrice) {
        List<Product> products =  productRepository.findAll();

        return products.stream()
                .filter(p -> minPrice == null || p.getPrice() >= minPrice)
                .filter(p -> maxPrice == null || p.getPrice() <= maxPrice)
                .map(p -> new ProductResponse(
                        p.getId(),
                        p.getName(),
                        p.getPrice()
                ))
                .toList();

    }

    public void createProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.name());
        product.setPrice(request.price());
        productRepository.save(product);
    }

    public Product getProductByName(String name) {
        return productRepository.getProductByName(name)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.getProductById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        if (request.name() != null && !request.name().isEmpty()) {
            product.setName(request.name());
        }
        if (request.price() != null) {
            product.setPrice(request.price());
        }

        productRepository.save(product);
        return new ProductResponse(id, product.getName(), product.getPrice());
    }
}
