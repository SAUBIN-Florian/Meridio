package dev.florian.meridio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.florian.meridio.models.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    Profile findByUsername(String username);
}
