package dev.florian.meridio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.florian.meridio.models.Acl;

public interface AclRepository extends JpaRepository<Acl, Long> {
    
}
