package dev.florian.meridio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.florian.meridio.models.Role;
import dev.florian.meridio.repositories.RoleRepository;

@Service
public class RoleService {
    
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void save(Role role) {
        this.roleRepository.save(role);
    }
}
