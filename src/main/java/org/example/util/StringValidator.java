package org.example.util;

import java.util.regex.Pattern;

public class StringValidator {

    public static boolean emailIsValid(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
        return EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean passwordIsValid(String password) {
        String PASSWORD_REGEX = "^[a-zA-Z0-9]{6,10}$";
        Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);
        return PASSWORD_PATTERN.matcher(password).matches();
    }

}