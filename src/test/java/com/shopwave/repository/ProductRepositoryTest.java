package com.shopwave.repository;

import com.shopwave.model.Category;
import com.shopwave.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void findByNameContainingIgnoreCase_ReturnsCorrectResults() {
        Category category = Category.builder()
                .name("Test Category")
                .build();
        categoryRepository.save(category);

        Product product1 = Product.builder()
                .name("Apple iPhone")
                .description("Smartphone")
                .price(BigDecimal.valueOf(999))
                .stock(10)
                .category(category)
                .build();
        
        Product product2 = Product.builder()
                .name("Green Apple")
                .description("Fruit")
                .price(BigDecimal.valueOf(2))
                .stock(100)
                .category(category)
                .build();

        Product product3 = Product.builder()
                .name("Samsung Galaxy")
                .description("Smartphone")
                .price(BigDecimal.valueOf(899))
                .stock(15)
                .category(category)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);

        List<Product> results = productRepository.findByNameContainingIgnoreCase("ApPlE");

        assertEquals(2, results.size());
        assertTrue(results.stream().anyMatch(p -> p.getName().equals("Apple iPhone")));
        assertTrue(results.stream().anyMatch(p -> p.getName().equals("Green Apple")));
    }
}
