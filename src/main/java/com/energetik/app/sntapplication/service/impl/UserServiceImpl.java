package com.energetik.app.sntapplication.service.impl;


import com.energetik.app.sntapplication.repository.UserRepository;
import com.energetik.app.sntapplication.security.User;
import com.energetik.app.sntapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existUserById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public void saveUser(User user) {
        if (user == null || user.getUsername() == null) {
            throw new IllegalArgumentException(String.format("User or user.username must be not null"));
        }
        Optional<User> optUser = userRepository.findUserByUsername(user.getUsername());
        if (optUser.isEmpty()) {
            User nUser = new User();
            nUser.setId(user.getId());
            nUser.setUsername(user.getUsername());
            nUser.setPassword(passwordEncoder.encode(user.getPassword()));
            nUser.setRoles(user.getRoles());
            userRepository.saveAndFlush(user);
        } else {
            throw new IllegalArgumentException(String.format("User with username: %s was exist", user.getUsername()));
        }
    }

    @Override
    public void updateUser(User user) {
        if (user == null || user.getUsername() == null) {
            throw new IllegalArgumentException(String.format("User or user.username must be not null"));
        }
        Optional<User> optUser = userRepository.findUserByUsername(user.getUsername());
        if (optUser.isPresent()) {
            User nUser = new User();
            nUser.setId(user.getId());
            nUser.setUsername(user.getUsername());
            nUser.setPassword(passwordEncoder.encode(user.getPassword()));
            nUser.setRoles(user.getRoles());
            userRepository.saveAndFlush(user);
        } else {
            throw new IllegalArgumentException(String.format("User with username: %s was not exist", user.getUsername()));
        }
    }

    @Override
    public void deleteUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException(String.format("User id must be not null"));
        }
        Optional<User> optUser = userRepository.findById(id);
        if (optUser.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException(String.format("User with id:%d was not exist", id));
        }
    }
}
