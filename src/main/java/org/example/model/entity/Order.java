package org.example.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;
    private double totalPrice;
    private boolean paymentStatus = false;
    @ManyToMany
    public List<Product> products;
    @OneToOne(cascade = CascadeType.ALL)
    public User user;

    public Order() {
        this.products = new ArrayList<>();
    }

    public boolean getPaymentStatus() {
        return paymentStatus;
    }

    public void updateTotalPrice() {
        totalPrice = 0;
        for (Product product : products)
            totalPrice += product.getPrice();
    }

    public void addProducts(List<Product> productIds) {
        products.addAll(productIds);
    }
}