package dev.florian.meridio.services;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.florian.meridio.models.File;
import dev.florian.meridio.repositories.FileRepository;
import jakarta.transaction.Transactional;

@Service
public class FileService {
    
    private final FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public List<File> findAll() {
        return this.fileRepository.findAll();
    }

    public File findById(Long fileId) {
        return this.fileRepository.findById(fileId).get();
    }

    @Transactional
    public void save(File newFile, Principal principal) {
        this.fileRepository.save(newFile);
    }

    public void deleteOne(Long id) throws Exception {
        if (this.fileRepository.existsById(id)) {
            this.fileRepository.deleteById(id);
        } else {
            throw new Exception("File not found, id: " + id);
        }
    }
}
