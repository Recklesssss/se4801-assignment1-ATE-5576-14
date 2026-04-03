package com.shopwave.service;

import com.shopwave.dto.CreateProductRequest;
import com.shopwave.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    
    ProductDTO createProduct(CreateProductRequest request);
    
    Page<ProductDTO> getAllProducts(Pageable pageable);
    
    ProductDTO getProductById(Long id);
    
    List<ProductDTO> searchProducts(String keyword, BigDecimal maxPrice);
    
    ProductDTO updateStock(Long id, int delta);
}
