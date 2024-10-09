package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.model.entity.Order;
import org.example.model.service.OrderService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @RequestMapping(method = RequestMethod.POST, value = "/users/{userId}/orders")
    public ResponseEntity<Order> save(@PathVariable long userId, @RequestBody List<Long> productIds) {
        return new ResponseEntity<>(orderService.save(userId, productIds), HttpStatus.CREATED);
    }

    @RequestMapping("/orders/{orderId}")
    public ResponseEntity<Order> getOrderByOrderId(@PathVariable long orderId) {
        return new ResponseEntity<>(orderService.getByOrderId(orderId), HttpStatus.OK);
    }

    @RequestMapping("/users/{userId}/orders/cart")
    public ResponseEntity<Order> getOrderByUserId(@PathVariable long userId) {
        return new ResponseEntity<>(orderService.getByUserId(userId), HttpStatus.OK);
    }

    @RequestMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        return new ResponseEntity<>(orderService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/users/{userId}/orders")
    public ResponseEntity<Order> updateOrderByUserId(@PathVariable long userId, @RequestBody List<Long> products) {
        return new ResponseEntity<>(orderService.updateByUserId(userId, products), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/orders/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable long orderId) {
        orderService.delete(orderId);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{userId}/orders")
    public ResponseEntity<String> deleteOrderByUserId(@PathVariable long userId) {
        orderService.deleteByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("orders/total_price")
    public ResponseEntity<Double> getTotalPriceOpenOrders() {
        return new ResponseEntity<>(orderService.getSumOfPaidOrders(), HttpStatus.OK);
    }

    @GetMapping("/test")
    public ResponseEntity<Void> redirect() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("https://www.google.com"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

}
