# Test-Driven Development (TDD) - README

## Overview of TDD

**Test-Driven Development (TDD)** is a software development methodology in which tests are written before writing the actual code. The process follows a simple, yet powerful cycle, often referred to as the **Red-Green-Refactor** cycle:

1. **Red**: Write a test that describes a small piece of functionality. The test initially fails, as the code to pass the test doesn't exist yet.
2. **Green**: Write just enough code to make the test pass. At this stage, the test should pass, but the code may not be optimal or clean.
3. **Refactor**: Refactor the code to improve its structure or readability without changing its functionality. The tests should continue to pass after refactoring.

TDD encourages writing **clean code** by enforcing a strict cycle of testing, writing, and improving the code. It helps in reducing bugs, simplifying complex systems, and ensuring that each part of the system is tested thoroughly.

---

## TDD Cycle

The fundamental cycle of TDD is as follows:

### 1. **Red (Write a Failing Test)**

- The first step in TDD is to write a test that **fails** because no code has been implemented yet to fulfill the test's requirements.
- **Goal**: The test should fail so that you can be sure it’s working as intended.
- Example: In this demo, we write a test for the `ProductController.createProduct()` method, which currently does not exist.

    ```java
    @Test
    public void testCreateProduct() throws Exception {
        Product product = new Product("Laptop", "Machine Lenovo", 10000.00, "Electronics");

        // Test fails as no controller method is implemented yet
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(product)))
                .andExpect(status().isCreated());  // This test will fail initially
    }
    ```

### 2. **Green (Write Code to Pass the Test)**

- Once the test is written, the next step is to write the **minimum code** necessary to make the test pass.
- The code is often **not optimal** at this stage, but it should be just enough to make the test succeed.
- Example: We implement the controller's `createProduct` method to handle the `POST` request and return the product:

    ```java
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
    ```

- Now the test passes because the controller method exists and works as expected, even though the code may not be optimized yet.

### 3. **Refactor (Clean Up the Code)**

- After the test passes, the next step is to **refactor** the code to improve its quality, readability, and efficiency without changing its behavior.
- You should ensure that your refactoring does not break the existing functionality, and the test must continue to pass after refactoring.

    Example refactor might involve simplifying the controller logic, breaking it into smaller methods, or improving naming conventions, but no logic should change:

    ```java
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
    ```

---

## Benefits of TDD

- **Improved Code Quality**: TDD encourages writing simple, modular code that passes tests. This results in cleaner and more maintainable code.
- **Less Debugging**: Since you are continuously writing tests and ensuring your code works, there is less need for debugging later on.
- **Clear Requirements**: TDD forces developers to clarify the requirements upfront through tests, which can help reduce ambiguity and miscommunication.
- **Better Refactoring**: With the safety net of tests, developers can refactor code without the fear of introducing bugs, as the tests ensure that nothing breaks.
- **Documentation**: Tests can serve as **living documentation** for the code, showing how the system is expected to behave under various conditions.

---

## TDD in Action - Example: Product API

In this demo project, TDD is used to implement a **Product CRUD API**. Below is a simplified flow of how TDD was applied to implement some of the key endpoints.

### 1. **Test: Create Product**
- We begin by writing a test for the `POST /api/products` endpoint to ensure that a product can be created.
  
### 2. **Write Code to Pass the Test**
- We then write the code for the `ProductController.createProduct()` method, making sure that it returns the expected `Product` when a `POST` request is made. 

### 3. **Refactor**
- After the test passes, we refactor the controller code to ensure it's clean, concise, and more maintainable. For example, we may add validation or improve the HTTP response status code handling.

---

## Best Practices for TDD

### 1. **Write Small, Focused Tests**
- Tests should focus on one small unit of functionality at a time. This helps keep the tests clear and prevents them from becoming too complex.

    **Bad Test**: A single test covering multiple features.
    **Good Test**: Each test focuses on a specific behavior or case.

### 2. **Use Mocks and Stubs**
- Use **mocking** frameworks (like **Mockito**) to isolate components, making tests faster and more isolated from external dependencies (e.g., databases, network calls).

### 3. **Keep Tests Fast**
- Tests should run **quickly**. This means minimizing reliance on external systems, like databases. Use in-memory databases or mock services where necessary.

### 4. **Write Readable Tests**
- Tests should serve as documentation for how the system should behave. Avoid overly complex assertions, and aim for clarity.

### 5. **Refactor Often**
- Refactor the code frequently while maintaining the tests. Refactoring should not break existing functionality. The safety net of passing tests ensures you can refactor with confidence.

---

## Common TDD Pitfalls

1. **Skipping Tests**: Sometimes developers jump straight to coding and skip writing tests. This breaks the TDD cycle and can lead to poorly tested code.
2. **Overengineering the Solution**: The goal of TDD is to write just enough code to pass the test. Avoid writing extra code that isn’t needed yet.
3. **Skipping Refactoring**: Skipping the refactoring step can lead to cluttered, hard-to-maintain code. Always refactor after getting the test to pass.

---

## Summary

Test-Driven Development is a powerful technique that helps ensure high-quality code and reliable software systems. By following the **Red-Green-Refactor** cycle, developers can ensure their code is thoroughly tested, clean, and maintainable. This project demonstrates how TDD can be applied to build a **Product CRUD API**, from writing tests to implementing the solution and refactoring the code.

With TDD:
- **You always write tests first** to drive your design.
- **You minimize bugs** by testing at every step.
- **You ensure maintainable, clean code** that can be easily refactored without introducing errors.

Start with simple tests, write just enough code to pass them, and keep refactoring—TDD makes coding more reliable and efficient!
