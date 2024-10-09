package org.example.model.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.model.entity.Product;
import org.example.model.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductService productService;

    private Product getProduct() {
        Product product = new Product();
        product.setBrand("samsung");
        product.setModel("S24");
        product.setColor("BLACK");
        product.setPrice(800.0);
        return product;
    }

    private Product getUpdatedProduct() {
        Product product = new Product();
        product.setBrand("SONY");
        product.setModel("S24");
        product.setColor("BLACK");
        product.setPrice(800.0);
        return product;
    }

    @Test
    @DisplayName("")
    void saveProduct_validProduct_Success() {
        Product expectedProduct = getProduct();
        when(productRepository.save(expectedProduct)).thenReturn(expectedProduct);
        Product resultProduct = productService.save(expectedProduct);
        assertEquals(expectedProduct, resultProduct);
        verify(productRepository, times(1)).save(expectedProduct);
    }

    @Test
    @DisplayName("")
    void getById_invalidId_throwsEntityNotfoundException() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> productService.getById(1L));
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("")
    void getAll_emptyProductList_Success() {
        List<Product> expectedList = Collections.emptyList();
        when(productRepository.findAll()).thenReturn(expectedList);
        List<Product> resultList = productService.getAll();
        assertEquals(expectedList, resultList);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("")
    void updateProduct_validProperty_Success() {
        Product product = getProduct();
        Product expectedProduct = getUpdatedProduct();
        Map<String, Object> updates = Map.of("brand", "SONY");
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(isA(Product.class))).thenReturn(expectedProduct);
        Product resultProduct = productService.update(1L, updates);
        assertEquals(expectedProduct, resultProduct);
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(expectedProduct);
    }

    @Test
    void delete() {
        productService.delete(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void getSumOfAllProducts_emptyProductList_Success() {
        List<Product> expectedList = Collections.emptyList();
        when(productRepository.findAll()).thenReturn(expectedList);
        double actualTotalPrice = productService.getSumOfAllProducts();
        assertEquals(0, actualTotalPrice);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductsById() {
        Product product1 = getProduct();
        Product product2 = getUpdatedProduct();
        List<Long> productIds = List.of(1L, 2L);
        List<Product> expectedProductList = List.of(product1, product2);
        when(productRepository.findAllById(productIds)).thenReturn(expectedProductList);
        List<Product> resultProductList = productService.getProductsById(productIds);
        assertEquals(expectedProductList, resultProductList);
        verify(productRepository, times(1)).findAllById(productIds);
    }

    @Test
    void setProductsOrder() {
    }
}