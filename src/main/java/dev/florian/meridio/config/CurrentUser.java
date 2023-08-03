package dev.florian.meridio.config;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import dev.florian.meridio.models.Profile;

public class CurrentUser implements UserDetails {

    private final Profile currentUser;

    public CurrentUser(Profile profile) {
        this.currentUser = profile;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.currentUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getPassword() {
        return this.currentUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.currentUser.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
