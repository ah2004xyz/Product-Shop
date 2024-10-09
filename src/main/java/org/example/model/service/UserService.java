package org.example.model.service;

import lombok.RequiredArgsConstructor;
import org.example.exception.BadRequestException;
import org.example.exception.NotFoundException;
import org.example.model.entity.Order;
import org.example.model.entity.User;
import org.example.model.repository.UserRepository;
import org.example.util.StringValidator;
import org.example.util.Validator;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        if (!Validator.isUserValid(user, userRepository)) {
            throw new BadRequestException();
        }
        return userRepository.save(user);
    }

    public User getById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found"));
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User update(long id, Map<String, Object> updates) {
        User existingUser = getById(id);
        updates.forEach((key, value) -> {
            switch (key) {
                case "email" -> {
                    if (StringValidator.emailIsValid((String) value))
                        existingUser.setEmail((String) value);
                }
                case "password" -> {
                    if (StringValidator.passwordIsValid((String) value))
                        existingUser.setPassword((String) value);
                }
                case "address" -> existingUser.setAddress((String) value);
                default -> throw new BadRequestException();
            }
        });
        return userRepository.save(existingUser);
    }

    public void delete(long id) {
            userRepository.deleteById(id);
    }

    public List<Order> getOrders(long id) {
        return getById(id).getOrders();
    }

}
