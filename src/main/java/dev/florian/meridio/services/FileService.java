package dev.florian.meridio.services;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.florian.meridio.models.File;
import dev.florian.meridio.models.Space;
import dev.florian.meridio.repositories.FileRepository;
import jakarta.transaction.Transactional;

@Service
public class FileService {
    
    private final String folderPath = "src/main/resources/static/assets/";
    private final long maxFileSize = 2097152; // 2 MB in bytes
    private final FileRepository fileRepository;
    private final SpaceService spaceService;

    @Autowired
    public FileService(FileRepository fileRepository, SpaceService spaceService) {
        this.fileRepository = fileRepository;
        this.spaceService = spaceService;
    }

    public List<File> findAll() {
        return this.fileRepository.findAll();
    }

    public File findById(Long fileId) {
        return this.fileRepository.findById(fileId).get();
    }

    @Transactional
    public void save(MultipartFile file, Long spaceId) {
        // Transform MultiPartFile into a POJO
        File newFile = new File(file.getOriginalFilename(), file.getContentType(), folderPath + file.getOriginalFilename());
        
        // Bind POJO file to the current space
        Space currentSpace = this.spaceService.findById(spaceId);
        currentSpace.setFiles(newFile);
        newFile.setSpace(currentSpace);

        // Create a File instance in /static/assets
        if (file.getSize() > maxFileSize) {
            throw new IllegalArgumentException("File size exceeds the allowed limit");
        }
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderPath + file.getOriginalFilename());
            try(OutputStream outputStream = Files.newOutputStream(path)) {
                outputStream.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // save POJO in db
        this.fileRepository.save(newFile);
    }

    public void deleteOne(Long id) throws Exception {
        if (this.fileRepository.existsById(id)) {
            this.fileRepository.deleteById(id);
        } else {
            throw new Exception("File not found, id: " + id);
        }
    }

    public boolean existsByFileName(String fileName) {
        return this.fileRepository.existsByFileName(fileName);
    }
}
