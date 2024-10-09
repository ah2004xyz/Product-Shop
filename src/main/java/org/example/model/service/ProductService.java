package org.example.model.service;

import org.example.exception.BadRequestException;
import org.example.exception.NotFoundException;
import org.example.model.entity.Product;
import org.example.model.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product getById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product with id " + id + " not found"));
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    //illegal arg exception?
    public Product update(long id, Map<String, Object> updates) {
        Product existingProduct = getById(id);
        updates.forEach((key, value) -> {
            switch (key) {
                case "brand" -> existingProduct.setBrand((String) value);
                case "model" -> existingProduct.setModel((String) value);
                case "color" -> existingProduct.setColor((String) value);
                case "price" -> existingProduct.setPrice(Double.parseDouble(value.toString()));
                default -> throw new BadRequestException();
            }
        });
        return productRepository.save(existingProduct);
    }

    public void delete(long id) {
            productRepository.deleteById(id);
    }

    public double getSumOfAllProducts() {
        List<Product> products = productRepository.findAll();
        double totalPrice = 0;
        for (Product product : products)
            totalPrice += product.getPrice();
        return totalPrice;
    }

    public List<Product> getProductsById(List<Long> productIds) {
        return productRepository.findAllById(productIds);
    }

}
