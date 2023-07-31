package dev.florian.meridio.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
public class Profile {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="profile_role", 
        joinColumns=@JoinColumn(name="profile_id" ,referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="role_id" ,referencedColumnName="id"))
    List<Role> roles = new ArrayList<>();

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<Space> spaces = new ArrayList<>();

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    private List<Acl> acls = new ArrayList<>();

    @DateTimeFormat
    private LocalDateTime createdAt;

    @DateTimeFormat
    private LocalDateTime updatedAt = null;

    public Profile() {}

    public Profile(@NotNull String username, @NotNull @Email String email, @NotNull String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @PrePersist
    public void init() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Space> getSpaces() {
        return spaces;
    }

    public void setSpaces(List<Space> spaces) {
        this.spaces = spaces;
    }

    public List<Acl> getAcls() {
        return acls;
    }

    public void setAcls(AclType aclType, Long spaceId) {
        for (Acl acl : this.acls) {
            if (acl.getSpace().getId().equals(spaceId)) {
                acl.setType(aclType);
                break;
            }
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Profile [id=" + id + ", username=" + username + ", email=" + email + ", roles=" + roles + "]";
    }

}
