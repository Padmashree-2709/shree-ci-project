package com.stepup.service;

import com.stepup.model.Product;
import com.stepup.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> searchProducts(String keyword, String category, Double minPrice, Double maxPrice, org.springframework.data.domain.Sort sort) {
        if (sort == null) sort = org.springframework.data.domain.Sort.unsorted();
        return productRepository.searchProducts(keyword, category, minPrice, maxPrice, sort);
    }
}
