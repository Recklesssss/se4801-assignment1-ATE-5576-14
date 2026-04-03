package com.shopwave.service;

import com.shopwave.dto.CreateProductRequest;
import com.shopwave.dto.ProductDTO;
import com.shopwave.mapper.ProductMapper;
import com.shopwave.model.Category;
import com.shopwave.model.Product;
import com.shopwave.repository.CategoryRepository;
import com.shopwave.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private CreateProductRequest createRequest;
    private Category category;
    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        createRequest = new CreateProductRequest();
        createRequest.setName("Test Product");
        createRequest.setPrice(BigDecimal.valueOf(100));
        createRequest.setStock(10);
        createRequest.setCategoryId(1L);

        category = Category.builder()
                .id(1L)
                .name("Test Category")
                .build();

        product = Product.builder()
                .id(1L)
                .name("Test Product")
                .price(BigDecimal.valueOf(100))
                .stock(10)
                .category(category)
                .build();

        productDTO = ProductDTO.builder()
                .id(1L)
                .name("Test Product")
                .price(BigDecimal.valueOf(100))
                .stock(10)
                .categoryId(1L)
                .build();
    }

    @Test
    void createProduct_HappyPath() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(createRequest);

        assertNotNull(result);
        assertEquals("Test Product", result.getName());
        assertEquals(1L, result.getCategoryId());

        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void createProduct_CategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            productService.createProduct(createRequest);
        });

        assertTrue(exception.getMessage().contains("Category not found"));
        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
    }
}
