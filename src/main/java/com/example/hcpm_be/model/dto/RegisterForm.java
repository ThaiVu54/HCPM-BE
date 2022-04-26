package com.example.hcpm_be.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class RegisterForm {
    private String fullName;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String national;

    private String brand;

    private String address;

    private String age;

    private String category;

    private String workspace;

    private Set<String> roles;
}
