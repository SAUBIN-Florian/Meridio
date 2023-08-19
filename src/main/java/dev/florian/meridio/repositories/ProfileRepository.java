package dev.florian.meridio.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.florian.meridio.models.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    Optional<Profile> findByUsername(String username);

    List<Profile> findTop10ByUsernameContaining(String query);

    Boolean existsByUsername(String username);
}
