package dev.florian.meridio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import dev.florian.meridio.dto.AclDto;
import dev.florian.meridio.models.Acl;
import dev.florian.meridio.models.Profile;
import dev.florian.meridio.models.Space;
import dev.florian.meridio.repositories.AclRepository;

@Service
public class AclService {
    
    private final AclRepository aclRepository;

    @Autowired
    public AclService(AclRepository aclRepository) {
        this.aclRepository = aclRepository;
    }

    @Transactional
    public void save(Acl acl) {
        this.aclRepository.save(acl);
    }

    @Transactional
    public void save(AclDto aclDto, Profile friend, Space currentSpace) {
        // Create a new Acl && set Profile and Space to it
        Acl newAcl = new Acl();
        newAcl.setType(aclDto.getAclType());
        newAcl.setProfile(friend);
        newAcl.setSpace(currentSpace);

        // Bind the fresh acl to the friend Profile & current Space
        currentSpace.setAcls(newAcl);
        friend.setAcls(newAcl);
        // TODO: Repair KEY_CONSTRAIN_VIOLATION on this:
        friend.setSpaces(currentSpace);

        // Finally store to the database
        this.aclRepository.save(newAcl);
    }
}
