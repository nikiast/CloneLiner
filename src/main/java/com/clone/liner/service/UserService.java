package com.clone.liner.service;

import com.clone.liner.model.user.Role;
import com.clone.liner.model.user.User;
import com.clone.liner.repository.UserRepository;
import com.clone.liner.util.UserUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public User create(User user) {
        user.setRole(Role.USER);
        user.setRegistrationTime(UserUtil.formatDateTimeNow());
        user.setActive(true);
        return user;
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public User selectRole(User user, String role) {
        switch (role) {
            case "PROVIDER" -> user.setRole(Role.PROVIDER);
            case "ADMIN" -> user.setRole(Role.ADMIN);
            default -> user.setRole(Role.USER);
        }
        return user;
    }

    public void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public List<User> getUsers(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(List::of).orElseGet(userRepository::findAll);
    }
}
