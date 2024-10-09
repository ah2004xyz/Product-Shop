package org.example.controller;

import org.example.model.entity.Order;
import org.example.model.entity.User;
import org.example.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @RequestMapping("/users/{userId}")
    public ResponseEntity<User> getById(@PathVariable long userId) {
        return new ResponseEntity<>(userService.getById(userId), HttpStatus.OK);
    }

    @RequestMapping("/users")
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/users/{userId}")
    public ResponseEntity<User> update(@PathVariable long userId, @RequestBody Map<String, Object> updates) {
        return new ResponseEntity<>(userService.update(userId, updates), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users/{userId}")
    public ResponseEntity<String> delete(@PathVariable long userId) {
        userService.delete(userId);
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/users/{userId}/orders")
    public ResponseEntity<List<Order>> getOrders(@PathVariable long userId) {
        return new ResponseEntity<>(userService.getOrders(userId), HttpStatus.OK);
    }

}
