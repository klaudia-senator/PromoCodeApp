package com.example.promocode_app.controller;

import com.example.promocode_app.model.Product;
import com.example.promocode_app.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {
    @InjectMocks
    private ProductController productController;
    @Mock
    private ProductService productService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateProduct() {
        Product product = new Product();
        product.setId(1);
        product.setName("Laptop");
        product.setPrice(BigDecimal.valueOf(1000.00));
        product.setCurrency("USD");

        when(productService.save(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(product);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).save(product);
    }
    @Test
    void testGetAllProducts() {
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");
        product1.setPrice(BigDecimal.valueOf(1000.00));
        product1.setCurrency("USD");

        Product product2 = new Product();
        product2.setId(1L);
        product2.setName("Phone");
        product2.setPrice(BigDecimal.valueOf(500.00));
        product2.setCurrency("PLN");

        List<Product> productList = Arrays.asList(product1, product2);

        when(productService.findAll()).thenReturn(productList);

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(productList, response.getBody());
        verify(productService, times(1)).findAll();
    }
    @Test
    void testGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");
        product.setPrice(BigDecimal.valueOf(1000.00));
        product.setCurrency("USD");

        when(productService.findById(1L)).thenReturn(Optional.of(product));

        ResponseEntity<Product> response = productController.getProductById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).findById(1L);
    }
    @Test
    void testUpdateProduct() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Updated Laptop");
        product.setPrice(BigDecimal.valueOf(1500.00));
        product.setCurrency("USD");

        when(productService.findById(1L)).thenReturn(Optional.of(product));
        when(productService.save(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.updateProduct(1L, product);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(product, response.getBody());
        verify(productService, times(1)).findById(1L);
        verify(productService, times(1)).save(product);
    }
}