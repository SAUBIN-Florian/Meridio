package dev.florian.meridio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.florian.meridio.models.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    Optional<Profile> findByUsername(String username);

    Boolean existsByUsername(String username);
}
