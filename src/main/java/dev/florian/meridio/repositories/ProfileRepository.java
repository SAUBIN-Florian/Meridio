package dev.florian.meridio.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.florian.meridio.models.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    
    Optional<Profile> findByUsername(String username);

    @Query("SELECT p from Profile p WHERE p.username LIKE CONCAT('%', :query, '%')")
    Page<Profile> searchUsers(@Param("query") String query, Pageable pageable);

    Boolean existsByUsername(String username);
}
