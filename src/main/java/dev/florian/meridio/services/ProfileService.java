package dev.florian.meridio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.florian.meridio.models.Profile;
import dev.florian.meridio.repositories.ProfileRepository;

@Service
public class ProfileService {
    

    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public List<Profile> findAll() {
        return this.profileRepository.findAll();
    }

    public Profile findById(Long id) {
        return this.profileRepository.findById(id).get();
    }

    public void save(Profile profile) {
        this.profileRepository.save(profile);
    }

    public void updateOne(Long id, Profile profile) throws Exception {
        Optional<Profile> optionalProfile = this.profileRepository.findById(id);
        if (optionalProfile.isPresent()) {

            Profile existingProfile = optionalProfile.get();

            existingProfile.setUsername(profile.getUsername());
            existingProfile.setEmail(profile.getEmail());

            this.profileRepository.save(existingProfile);
        } else {
            throw new Exception("Profile not found, username: " + profile.getUsername());
        }
    }

    public void deleteOne(Long id) throws Exception {
        if (this.profileRepository.existsById(id)) {
            this.profileRepository.deleteById(id);
        } else {
            throw new Exception("Profile not found, id: " + id);
        }
    }
}
