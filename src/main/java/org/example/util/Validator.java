package org.example.util;

import org.example.model.entity.User;
import org.example.model.repository.UserRepository;

public class Validator {

    private Validator() {
    }

    public static boolean isUserValid(User user, UserRepository userRepository) {
        return StringValidator.emailIsValid(user.getEmail()) &&
                StringValidator.passwordIsValid(user.getPassword()) &&
                !userRepository.existsByEmail(user.getEmail());
    }

}