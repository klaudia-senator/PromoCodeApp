package com.example.promocode_app.service;

import com.example.promocode_app.model.Product;
import com.example.promocode_app.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Product save(Product product) {
        return productRepository.save(product);
    }
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

}
