package dev.florian.meridio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.florian.meridio.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
}
