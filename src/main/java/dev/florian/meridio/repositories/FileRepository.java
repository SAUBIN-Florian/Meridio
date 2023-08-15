package dev.florian.meridio.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.florian.meridio.models.File;

public interface FileRepository extends JpaRepository<File, Long> {

    boolean existsByFileName(String fileName);
    
}
