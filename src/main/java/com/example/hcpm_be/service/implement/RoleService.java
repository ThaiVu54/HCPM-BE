package com.example.hcpm_be.service.implement;


import com.example.hcpm_be.model.entity.Role;
import com.example.hcpm_be.model.entity.RoleName;
import com.example.hcpm_be.repository.RoleRepository;
import com.example.hcpm_be.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<Role> findByName(RoleName roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public Iterable<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
