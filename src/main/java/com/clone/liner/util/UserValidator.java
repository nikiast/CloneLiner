package com.clone.liner.util;

import com.clone.liner.model.user.User;
import com.clone.liner.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {
    private final UserRepository userRepository;
    private static final String PASSWORD_VALID = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[\\w]{6,}";
    private static final String EMAIL_VALID = "\\w+([\\.-]?\\w+)*@(\\w{2,5}[\\.]\\w{2,3})";

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        usernameValidate(user, errors);
        emailValidate(user, errors);
        passwordValidate(user, errors);
    }

    public void usernameValidate(User user, Errors errors) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            errors.rejectValue("username", "", "This username is exist");
        }
    }

    public void emailValidate(User user, Errors errors) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is exist");
        }
    }

    public void passwordValidate(User user, Errors errors) {
        if (!passwordRegExpValidate(user.getPassword())) {
            errors.rejectValue("password", "", "This password is not valid");
        }
    }

    private boolean passwordRegExpValidate(String password) {
        return Pattern.matches(PASSWORD_VALID, password);
    }

    private boolean emailRegExpValidate(String email) {
        return Pattern.matches(EMAIL_VALID, email);
    }
}
