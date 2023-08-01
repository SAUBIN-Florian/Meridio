package dev.florian.meridio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.florian.meridio.models.Profile;
import dev.florian.meridio.repositories.ProfileRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ProfileRepository repository;

    @Autowired
    public CustomUserDetailsService(ProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Profile profile = this.repository.findByUsername(username).get();
        return new CurrentUser(profile);
    }
    
}
