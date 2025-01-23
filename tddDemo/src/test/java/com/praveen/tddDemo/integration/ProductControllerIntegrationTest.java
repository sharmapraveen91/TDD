package com.praveen.tddDemo.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.praveen.tddDemo.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerIntegrationTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;


    @LocalServerPort
    private int port;

    private static final String BASE_URL = "/api/products";
    private Product product;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }

    @BeforeEach
    public void setup(){
        product = new Product(
                "Laptop",  "Lenovo ThinkPad", 1000.00, "Electronics"
        );

    }

    @Test
    public void test_create_product() throws Exception {
        String productJson = objectMapper.writeValueAsString(product);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(productJson, httpHeaders);
        String fullUrl = "http://localhost:" + port + BASE_URL;
        ResponseEntity<Product> response = restTemplate.exchange(fullUrl, HttpMethod.POST, entity, Product.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Product createdProduct = response.getBody();
        assertNotNull(createdProduct);
        assertEquals("Laptop", createdProduct.getName());
        assertEquals("Lenovo ThinkPad", createdProduct.getDescription());
        assertEquals(1000.00, createdProduct.getPrice());
        assertEquals("Electronics", createdProduct.getCategory());
    }

    @Test
    public void test_get_product() throws Exception{
        long productid = 1L;

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String fullUrl = "http://localhost:" + port + BASE_URL+"/1";
        ResponseEntity<Product> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, Product.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());




    }
}
