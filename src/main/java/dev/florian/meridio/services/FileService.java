package dev.florian.meridio.services;

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
        String filePath = "http://localhost/static/assets/";
        File newFile = new File(file.getOriginalFilename(), file.getContentType(), filePath + file.getOriginalFilename());
        
        // Bind POJO file to the current space
        Space currentSpace = this.spaceService.findById(spaceId);
        currentSpace.setFiles(newFile);
        newFile.setSpace(currentSpace);

        // Create a File instance in classpath:/static/assets


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
}
