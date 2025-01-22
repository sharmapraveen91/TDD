package com.praveen.tddDemo.service;

import com.praveen.tddDemo.exceptions.ProductNotFoundException;
import com.praveen.tddDemo.model.Product;
import com.praveen.tddDemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    public List<Product> fetchAllProducts() {
        return repository.findAll();
    }

    public void deleteProduct(long id) {
        Product product = findProductById(id); // Refactored to avoid duplication
        repository.delete(product);
    }

    public Product getProductById(long id) {
        return findProductById(id); // Refactored to avoid duplication
    }

    public Product updateProduct(long id, Product updatedProduct) {
        Product existingProduct = findProductById(id); // Refactored to avoid duplication
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCategory(updatedProduct.getCategory());
        return repository.save(existingProduct);
    }

    // Centralized method to fetch a product and throw ProductNotFoundException if not found
    private Product findProductById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
}

