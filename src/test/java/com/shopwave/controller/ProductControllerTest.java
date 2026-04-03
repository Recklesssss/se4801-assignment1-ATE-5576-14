package com.shopwave.controller;

import com.shopwave.dto.ProductDTO;
import com.shopwave.exception.ProductNotFoundException;
import com.shopwave.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void getAllProducts_Returns200WithPaginatedBody() throws Exception {
        ProductDTO product1 = ProductDTO.builder().id(1L).name("Product 1").price(BigDecimal.valueOf(10)).build();
        ProductDTO product2 = ProductDTO.builder().id(2L).name("Product 2").price(BigDecimal.valueOf(20)).build();
        
        Page<ProductDTO> productPage = new PageImpl<>(List.of(product1, product2), PageRequest.of(0, 10), 2);

        Mockito.when(productService.getAllProducts(any())).thenReturn(productPage);

        mockMvc.perform(get("/api/products?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.totalElements").value(2));
    }

    @Test
    void getProductById_NotFound_Returns404() throws Exception {
        Mockito.when(productService.getProductById(999L))
                .thenThrow(new ProductNotFoundException("Product not found with ID: 999"));

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Product not found with ID: 999"))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}
