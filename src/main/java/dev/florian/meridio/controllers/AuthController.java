package dev.florian.meridio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.florian.meridio.models.Profile;
import dev.florian.meridio.models.Role;
import dev.florian.meridio.repositories.ProfileRepository;
import dev.florian.meridio.repositories.RoleRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    private final ProfileRepository profileRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(ProfileRepository profileRepository, RoleRepository roleRepository,
            AuthenticationManager authManager, PasswordEncoder passwordEncoder) {
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String register(Profile profile) {
        return "authentication/register";
    }

    @PostMapping("/register")
    public String registerValidation(@Valid Profile profile, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "authentication/register";
        } else {
            Role defaultUserRole = new Role("USER");
            Profile newUser = new Profile(profile.getUsername(), 
                    profile.getEmail(),
                    passwordEncoder.encode(profile.getPassword()));
            newUser.setRoles(defaultUserRole);

            if(this.profileRepository.existsByUsername(profile.getUsername())) {
                throw new IllegalArgumentException("Username already exists.");
            }

            this.roleRepository.save(defaultUserRole);
            this.profileRepository.save(newUser);
    
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/login")
    public String login(Profile profile) {
        return "authentication/login";
    }

    @PostMapping("/login")
    public String loginValidation(@Valid Profile profile, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "authentication/login";
        } else {
            return "redirect:/";
        }
    }
}
