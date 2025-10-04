package com.azobioz.api.repository;

import com.azobioz.api.module.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> getProductByName(String name);
    Optional<Product> getProductById(Long id);
}
