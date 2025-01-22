package com.praveen.tddDemo.repository;

import com.praveen.tddDemo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
