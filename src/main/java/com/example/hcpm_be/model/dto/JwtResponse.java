package com.example.hcpm_be.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String name;
    private String username;
    private String avatar;
    private String email;
    private Collection<? extends GrantedAuthority> roles;
    private String phone;
    private String national;

    public JwtResponse(String token, String name, String username, String avatar, Collection<? extends GrantedAuthority> roles, String phone) {
        this.token = token;
        this.name = name;
        this.username = username;
        this.avatar = avatar;
        this.roles = roles;
        this.phone = phone;
    }

    public JwtResponse(String token, String name, String username, String avatar, String phone, String email, Collection<? extends GrantedAuthority> roles) {
        this.token = token;
        this.name = name;
        this.username = username;
        this.avatar = avatar;
        this.phone = phone;
        this.email = email;
        this.roles = roles;
    }

    public JwtResponse(String token, String name, String username, String avatar, String phone, String email, Collection<? extends GrantedAuthority> roles, String national) {
        this.token = token;
        this.name = name;
        this.username = username;
        this.avatar = avatar;
        this.phone = phone;
        this.email = email;
        this.roles = roles;
        this.national=national;
    }


//    public JwtResponse(String token, String name, String avatar, Collection<? extends GrantedAuthority> authorities) {
//        this.token = token;
//        this.name = name;
//        this.avatar = avatar;
//        this.roles = authorities;
//    }


}
