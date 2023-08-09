package dev.florian.meridio.services;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.florian.meridio.models.Acl;
import dev.florian.meridio.models.AclType;
import dev.florian.meridio.models.Profile;
import dev.florian.meridio.models.Space;
import dev.florian.meridio.repositories.SpaceRepository;
import jakarta.transaction.Transactional;

@Service
public class SpaceService {
    private final SpaceRepository spaceRepository;
    private final AclService aclService;
    private final ProfileService profileService;

    @Autowired
    public SpaceService(SpaceRepository spaceRepository, AclService aclService, ProfileService profileService) {
        this.spaceRepository = spaceRepository;
        this.aclService = aclService;
        this.profileService = profileService;
    }

    public List<Space> findAll() {
        return this.spaceRepository.findAll();
    }

    public Space findById(Long id) {
        return this.spaceRepository.findById(id).get();
    }

    @Transactional
    public void save(Space space, Principal principal) {
        // 1. Find & assign author of the space
        Profile authorProfile = this.profileService.findByUsername(principal.getName());
        space.setProfile(authorProfile);
        authorProfile.setSpaces(space);

        // 2. Create & assign acl for the space & the author profile
        Acl adminAcl = new Acl(AclType.ADMIN);
        adminAcl.setProfile(authorProfile);
        adminAcl.setSpace(space);
        authorProfile.setAcls(adminAcl);
        space.setAcls(adminAcl);

        // 3. Finally save the space & acl
        this.spaceRepository.save(space);
        this.aclService.save(adminAcl);
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
