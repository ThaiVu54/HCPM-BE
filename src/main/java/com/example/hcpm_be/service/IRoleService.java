package com.example.hcpm_be.service;


import com.example.hcpm_be.model.entity.Role;
import com.example.hcpm_be.model.entity.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName roleName);
    Iterable<Role> findAll();
    Role save(Role role);
}
