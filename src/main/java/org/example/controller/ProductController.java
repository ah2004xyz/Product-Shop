package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.entity.Product;
import org.example.model.service.ProductService;
import org.hibernate.PropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @RequestMapping(method = RequestMethod.POST, value = "/products")
    public ResponseEntity<Object> addProduct(@RequestBody Product product) {
        try {
            return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
        } catch (PropertyValueException e) {
            String errorMessage = "Invalid request payload. Please provide all required fields.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping("/products/{productId}")
    public ResponseEntity<Product> getById(@PathVariable long productId) {
        return new ResponseEntity<>(productService.getById(productId), HttpStatus.OK);
    }

    @RequestMapping("/products")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/products/{productId}")
    public ResponseEntity<Product> update(@PathVariable long productId, @RequestBody Map<String, Object> updates) {
        return new ResponseEntity<>(productService.update(productId, updates), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/products/{productId}")
    public ResponseEntity<String> delete(@PathVariable long productId) {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/products/total_price")
    public ResponseEntity<Double> getTotalPrice() {
        return new ResponseEntity<>(productService.getSumOfAllProducts(), HttpStatus.OK);
    }

}
