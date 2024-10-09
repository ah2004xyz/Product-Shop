package org.example.model.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.NotFoundException;
import org.example.model.entity.Order;
import org.example.model.entity.Product;
import org.example.model.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;

    public Order save(long userId, List<Long> productIds) {
        Order order =
                orderRepository.findByUserUserIdAndPaymentStatus(userId, false);
        if (order == null) {
            order = getNewOrder(userId);
        }
        return setRelationsAndSave(productIds, order);
    }

    private Order setRelationsAndSave(List<Long> productIds, Order order) {
        order.addProducts(getProductsById(productIds));
        order.updateTotalPrice();
        return orderRepository.save(order);
    }

    private List<Product> getProductsById(List<Long> productIds) {
        return productService.getProductsById(productIds);
    }

    private Order getNewOrder(long userId) {
        Order order = new Order();
        order.setUser(userService.getById(userId));
        userService.getOrders(userId).add(order);
        return order;
    }

    public Order getByUserId(long userId) {
        Order order = orderRepository.findByUserUserIdAndPaymentStatus(userId, false);
        if (order == null)
            throw new NotFoundException("Order with not found");
        return order;
    }

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order updateByUserId(long userId, List<Long> productIds) {//void?
        Order order = orderRepository.findByUserUserIdAndPaymentStatus(userId, false);
        if (order == null){
            throw new NotFoundException("Order with not found");
        }
        List<Product> products = getProductsById(productIds);
        order.setProducts(products);
        order.updateTotalPrice();
        return orderRepository.save(order);
    }

    public void delete(long orderId) {
        try {
            orderRepository.deleteById(orderId);
        } catch (IllegalArgumentException e) {
            throw new NotFoundException("Order with id " + orderId + " not found");
        }
    }

    public void deleteByUserId(long userId) {
            orderRepository.deleteByUserUserIdAndPaymentStatus(userId, false);
    }

    public double getSumOfPaidOrders() {
        List<Order> orders = orderRepository.findAllByPaymentStatus(false);
        double totalPrice = 0;
        for (Order order : orders) {
            totalPrice += order.getTotalPrice();
        }
        return totalPrice;
    }

    public Order getByOrderId(long orderId) {
        Optional<Order> foundOrder = orderRepository.findById(orderId);
        if (foundOrder.isEmpty())
            throw new NotFoundException("Order with id " + orderId + " not found");
        return foundOrder.get();
    }

}
