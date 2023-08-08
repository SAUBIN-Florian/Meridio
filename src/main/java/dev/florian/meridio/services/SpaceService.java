package dev.florian.meridio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.florian.meridio.models.Space;
import dev.florian.meridio.repositories.SpaceRepository;

@Service
public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final AclService aclService;

    @Autowired
    public SpaceService(SpaceRepository spaceRepository, AclService aclService) {
        this.spaceRepository = spaceRepository;
        this.aclService = aclService;
    }

    public List<Space> findAll() {
        return this.spaceRepository.findAll();
    }

    public Space findById(Long id) {
        return this.spaceRepository.findById(id).get();
    }

    public void save(Space space) {
        this.spaceRepository.save(space);
    }

    public void updateOne(Long id, Space space) throws Exception {
        Optional<Space> optionalSpace = this.spaceRepository.findById(id);
        if (optionalSpace.isPresent()) {

            Space existingSpace = optionalSpace.get();

            existingSpace.setTitle(space.getTitle());

            this.spaceRepository.save(existingSpace);
        } else {
            throw new Exception("Space not found, title: " + space.getTitle());
        }
    }

    public void deleteOne(Long id) throws Exception {
        if (this.spaceRepository.existsById(id)) {
            this.spaceRepository.deleteById(id);
        } else {
            throw new Exception("Space not found, id: " + id);
        }
    }
}
