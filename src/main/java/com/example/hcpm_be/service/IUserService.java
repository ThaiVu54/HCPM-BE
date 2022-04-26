package com.example.hcpm_be.service;

import com.example.hcpm_be.model.entity.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Iterable<User> findUserByFullNameContaining(String fullName);

    Iterable<User> findUserByUsernameContaining(String username);

    User save(User user);

    Optional<User> findUserById(Long id);

    void remove(Long id);

    Iterable<User> findAll();

}
