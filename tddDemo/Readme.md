# TDD Demo Project - Product CRUD API

## Overview

This project demonstrates how to implement a **Product CRUD API** using **Test-Driven Development (TDD)** with **Spring Boot** and **JUnit**. The project includes tests for the core functionality of the product API, such as creating, updating, deleting, and retrieving products. The tests are written first, and the corresponding service and controller code is then written to pass the tests.

The project follows the **TDD cycle**:
1. **Red**: Write a failing test.
2. **Green**: Write just enough code to pass the test.
3. **Refactor**: Clean up the code while ensuring all tests continue to pass.

## Technologies Used
- **Spring Boot 3.4.1** (Web, JPA, and Test)
- **JUnit 5** (for unit testing)
- **MockMvc** (for testing REST controllers)
- **Mockito** (for mocking dependencies)
- **H2 Database** (for in-memory database testing)

## Features
- **Create a Product**: Allows adding new products to the system.
- **Update a Product**: Allows updating product details.
- **Delete a Product**: Allows deleting a product by its ID.
- **Get Product by ID**: Fetch a specific product based on its ID.
- **Get All Products**: Fetch a list of all products in the system.

---

## Project Setup

1. **Clone the repository**:

    ```bash
    git clone https://github.com/yourusername/tdd-demo-project.git
    cd tdd-demo-project
    ```

2. **Build the project**:

   Using Maven:
    ```bash
    mvn clean install
    ```

3. **Run the project**:
   You can run the project using:

    ```bash
    mvn spring-boot:run
    ```

   This will start the Spring Boot application on `http://localhost:8080`.

4. **Run Tests**:
   The tests can be executed using:

    ```bash
    mvn test
    ```

   This will run all unit tests, which include tests for the **ProductController** and **ProductService** classes.

---

## TDD Explanation and Test Details

### What is TDD?

Test-Driven Development (TDD) is a software development approach where you write tests **before** writing the actual code. The process follows a simple cycle:
1. **Red**: Write a test that fails.
2. **Green**: Write the minimum code required to make the test pass.
3. **Refactor**: Clean up the code, ensuring that it still works and passes all tests.

TDD helps developers write cleaner, bug-free code and reduces the risk of defects when refactoring or extending functionality.

### TDD Cycle in the Demo

In this project, we follow the TDD cycle for each functionality. For example:

1. **Test: Create Product**:  
   In the `ProductControllerTest` class, we start by writing a test for the `POST /api/products` endpoint. The test is written first, specifying the expected input (`Product` details) and the expected output (the HTTP status and the returned data). At this point, the test will fail because the implementation of the controller is not yet present.

2. **Write Code to Pass the Test**:  
   Next, we implement the `ProductController` class and its `createProduct()` method to handle the HTTP request and return a `Product` object. This will make the test pass.

3. **Refactor**:  
   After the test passes, we clean up the code for better readability and structure. Refactoring is done while ensuring that the tests still pass.

### Test Scenarios Covered

The `ProductControllerTest` class covers various CRUD operations and edge cases:

- **testCreateProduct**: Tests creating a product via the `POST /api/products` endpoint.
- **testUpdateProduct**: Tests updating a product via the `PUT /api/products/{id}` endpoint.
- **testUpdateProduct_notFound**: Tests updating a non-existing product, which should return a `404 Not Found` error.
- **testGetProduct**: Tests retrieving a product by its ID via the `GET /api/products/{id}` endpoint.
- **testGetAllProducts**: Tests retrieving a list of all products via the `GET /api/products/all` endpoint.
- **testGetAllProducts_emptyList**: Tests retrieving an empty list of products.
- **testDeleteProduct**: Tests deleting a product via the `DELETE /api/products/{id}` endpoint.
- **testDeleteProduct_notFound**: Tests attempting to delete a non-existing product, which should return a `404 Not Found` error.

Each test uses **MockMvc** to simulate HTTP requests and check the responses. Dependencies like **Mockito** are used to mock the `ProductService` to isolate the controller from the actual business logic.

### Mocking with Mockito

In this project, we use **Mockito** to mock the `ProductService` so that we don’t need to interact with a real database or service during testing. This ensures that tests run quickly and are focused only on testing the controller’s behavior.

```java
@MockitoBean
private ProductService productService;
