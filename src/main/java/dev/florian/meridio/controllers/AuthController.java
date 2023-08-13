package dev.florian.meridio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.florian.meridio.models.Profile;
import dev.florian.meridio.models.Role;
import dev.florian.meridio.services.ProfileService;
import dev.florian.meridio.services.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    private final ProfileService profileService;
    private final RoleService roleService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(ProfileService profileService, RoleService roleService, 
            AuthenticationManager authManager,
            PasswordEncoder passwordEncoder) {
        this.profileService = profileService;
        this.roleService = roleService;
        this.authManager = authManager;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String register(Profile profile) {
        return "authentication/register";
    }

    @PostMapping("/register")
    public String registerValidation(@Valid Profile profile, BindingResult bindingResult) throws IllegalArgumentException {
        if(bindingResult.hasErrors()) {
            return "authentication/register";
        } else {
            Role defaultUserRole = new Role("USER");
            Profile newUser = new Profile(profile.getUsername(), 
                    profile.getEmail(),
                    passwordEncoder.encode(profile.getPassword()));
            newUser.setRoles(defaultUserRole);

            if(this.profileService.existsByUsername(profile.getUsername())) {
                throw new IllegalArgumentException("Username already exists.");
            }

            this.roleService.save(defaultUserRole);
            this.profileService.save(newUser);
    
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
            Authentication authentication = this.authManager.authenticate(
                new UsernamePasswordAuthenticationToken(profile.getUsername(), profile.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/";
        }
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/auth/login";
    }
}
