package dev.florian.meridio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.florian.meridio.models.Space;

public interface SpaceRepository extends JpaRepository<Space, Long> {
    
}
