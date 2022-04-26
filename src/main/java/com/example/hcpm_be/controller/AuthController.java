package com.example.hcpm_be.controller;

import com.example.hcpm_be.model.dto.JwtResponse;
import com.example.hcpm_be.model.dto.LoginForm;
import com.example.hcpm_be.model.dto.Message;
import com.example.hcpm_be.model.dto.RegisterForm;
import com.example.hcpm_be.model.entity.Role;
import com.example.hcpm_be.model.entity.RoleName;
import com.example.hcpm_be.model.entity.User;
import com.example.hcpm_be.security.jwt.JwtProvider;
import com.example.hcpm_be.security.userprincal.UserPrincipal;
import com.example.hcpm_be.service.IRoleService;
import com.example.hcpm_be.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> sigup(@Valid @RequestBody RegisterForm registerForm) {
        if (userService.existsByEmail(registerForm.getEmail())) {
            return new ResponseEntity<>(new Message("Fail -> Email is already"), HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByUsername(registerForm.getUsername())) {
            return new ResponseEntity<>(new Message("Fail -> username is already"), HttpStatus.BAD_REQUEST);
        }

        User user = new User(registerForm.getFullName(), registerForm.getEmail(), registerForm.getUsername(), passwordEncoder.encode(registerForm.getPassword()));
        Set<String> strRoles = registerForm.getRoles();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(adminRole);
                    user.setAvatar("https://firebasestorage.googleapis.com/v0/b/hcpm-eab2d.appspot.com/o/admin.png?alt=media&token=9f080cab-d7be-4a47-bcdb-585d14c8591e");
                    break;
                case "employee":
                    Role employeeRole = roleService.findByName(RoleName.ROLE_EMPLOYEE).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(employeeRole);
                    user.setAvatar("https://firebasestorage.googleapis.com/v0/b/hcpm-eab2d.appspot.com/o/28d4a7118cdd42831bcc.jpg?alt=media&token=18763c72-c3e1-4908-85b0-65678e4aeac2");
                    user.setPhone(registerForm.getPhone());
                    user.setNational(registerForm.getNational());
                    break;
                case "customer":
                    Role customer = roleService.findByName(RoleName.ROLE_CUSTOMER).orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                    roles.add(customer);
                    user.setAvatar("https://firebasestorage.googleapis.com/v0/b/hcpm-eab2d.appspot.com/o/28d4a7118cdd42831bcc.jpg?alt=media&token=18763c72-c3e1-4908-85b0-65678e4aeac2");
                    user.setPhone(registerForm.getPhone());
                    user.setNational(registerForm.getNational());
                    break;
            }
        });
        user.setRoles(roles);
        user.setDateStart(LocalDate.now());
        userService.save(user);
        return new ResponseEntity<>(new Message("Register successful"), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrincipal userPrinciple = (UserPrincipal) authentication.getPrincipal();
        Optional<User> currentUser = userService.findByUsername(loginForm.getUsername());
        User user = currentUser.get();
        userService.save(currentUser.get());
        if (userPrinciple.getRoles().equals(RoleName.ROLE_ADMIN)) {
            return new ResponseEntity<>(new JwtResponse(token, userPrinciple.getFullName(), userPrinciple.getUsername(), userPrinciple.getAvatar(), user.getPhone(), userPrinciple.getEmail(), userPrinciple.getRoles()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new JwtResponse(token, userPrinciple.getFullName(), userPrinciple.getUsername(), userPrinciple.getAvatar(), user.getPhone(), userPrinciple.getEmail(), userPrinciple.getRoles(), user.getNational()), HttpStatus.OK);
        }
    }
}
