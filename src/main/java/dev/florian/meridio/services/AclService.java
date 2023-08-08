package dev.florian.meridio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.florian.meridio.models.Acl;
import dev.florian.meridio.repositories.AclRepository;

@Service
public class AclService {
    
    private final AclRepository aclRepository;

    @Autowired
    public AclService(AclRepository aclRepository) {
        this.aclRepository = aclRepository;
    }

    public void save(Acl acl) {
        this.aclRepository.save(acl);
    }
}
