package com.example.hcpm_be.service.implement;


import com.example.hcpm_be.model.entity.User;
import com.example.hcpm_be.repository.UserRepository;
import com.example.hcpm_be.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Iterable<User> findUserByFullNameContaining(String fullName) {
        return userRepository.findUserByFullNameContaining(fullName);
    }

    @Override
    public Iterable<User> findUserByUsernameContaining(String username) {
        return userRepository.findUserByUsernameContaining(username);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
