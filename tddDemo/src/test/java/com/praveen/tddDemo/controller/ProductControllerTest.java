package com.praveen.tddDemo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praveen.tddDemo.exceptions.ProductNotFoundException;
import com.praveen.tddDemo.model.Product;
import com.praveen.tddDemo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    // Test: Create product
    @Test
    public void testCreateProduct() throws Exception {
        // Given a product to create
        Product product = new Product("Laptop", "Machine Lenovo", 10000.00, "Electronics");

        // When saveProduct is called, return the product
        when(productService.saveProduct(any(Product.class))).thenReturn(product);

        // Perform the POST request and assert the response
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.description").value("Machine Lenovo"))
                .andExpect(jsonPath("$.category").value("Electronics"))
                .andExpect(jsonPath("$.price").value(10000.00));
    }

    // Test: Update product (successful)
    @Test
    public void testUpdateProduct() throws Exception {
        Product updatedProduct = new Product("Iphone-15 Pro", "Apple Iphone 15", 150000.00, "Electronics");
        updatedProduct.setId(1L); // Set the product ID

        // When updateProduct is called, return the updated product
        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(updatedProduct);

        // Perform the PUT request and assert the response
        mockMvc.perform(put("/api/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Iphone-15 Pro"))
                .andExpect(jsonPath("$.description").value("Apple Iphone 15"))
                .andExpect(jsonPath("$.price").value(150000.00))
                .andExpect(jsonPath("$.category").value("Electronics"));
    }

    // Test: Update product (not found)
    @Test
    public void testUpdateProduct_notFound() throws Exception {
        Product updatedProduct = new Product("Iphone", "Apple Iphone", 10000.00, "Electronics");
        long productId = 999L;
        updatedProduct.setId(productId);

        // Simulate ProductNotFoundException when updating
        doThrow(new ProductNotFoundException("Product Not Found")).when(productService)
                .updateProduct(eq(productId), any(Product.class));

        // Perform the PUT request and assert the response
        mockMvc.perform(put("/api/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedProduct)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product Not Found"));
    }

    // Test: Get product (found)
    @Test
    public void testGetProduct() throws Exception {
        Product mockProduct = new Product("Laptop", "Machine Lenovo", 10000.00, "Electronics");

        // When getProductById is called, return the mock product
        when(productService.getProductById(1L)).thenReturn(mockProduct);

        // Perform the GET request and assert the response
        mockMvc.perform(get("/api/products/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"))
                .andExpect(jsonPath("$.description").value("Machine Lenovo"))
                .andExpect(jsonPath("$.price").value(10000.00))
                .andExpect(jsonPath("$.category").value("Electronics"));
    }

    // Test: Get all products (non-empty list)
    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> products = List.of(
                new Product("Laptop", "Machine Lenovo", 10000.00, "Electronics"),
                new Product("Mobile Samsung A-12", "Samsung A12", 12000.00, "Electronics"),
                new Product("Mobile Iphone-15", "Apple IPhone15", 100000.00, "Electronics")
        );

        // When fetchAllProducts is called, return the list of products
        when(productService.fetchAllProducts()).thenReturn(products);

        // Perform the GET request and assert the response
        mockMvc.perform(get("/api/products/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(3))
                .andExpect(jsonPath("$.[0].name").value("Laptop"))
                .andExpect(jsonPath("$.[1].name").value("Mobile Samsung A-12"))
                .andExpect(jsonPath("$.[2].name").value("Mobile Iphone-15"));
    }

    // Test: Get all products (empty list)
    @Test
    public void testGetAllProducts_emptyList() throws Exception {
        // Simulate empty product list
        when(productService.fetchAllProducts()).thenReturn(Collections.emptyList());

        // Perform the GET request and assert the response
        mockMvc.perform(get("/api/products/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0)); // Expect empty list
    }

    // Test: Delete product (successful)
    @Test
    public void testDeleteProduct() throws Exception {
        long productId = 1L;

        // Simulate successful deletion
        doNothing().when(productService).deleteProduct(productId);

        // Perform the DELETE request and assert the response
        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNoContent()); // Expect HTTP 204 No Content
    }

    // Test: Delete product (not found)
    @Test
    public void testDeleteProduct_notFound() throws Exception {
        long productId = 999L;

        // Simulate ProductNotFoundException when deleting
        doThrow(new ProductNotFoundException("Product Not Found")).when(productService).deleteProduct(productId);

        // Perform the DELETE request and assert the response
        mockMvc.perform(delete("/api/products/{id}", productId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product Not Found"));
    }
}

