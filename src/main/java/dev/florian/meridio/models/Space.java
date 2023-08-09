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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;

@Entity
public class Space {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @ManyToOne()
    private Profile profile;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Acl> acls = new ArrayList<>();

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    List<File> files = new ArrayList<>();

    @DateTimeFormat
    private LocalDateTime createdAt;

    @DateTimeFormat
    private LocalDateTime updatedAt = null;

    public Space() {}

    public Space(String title) {
        this.title = title;
    }

    @PrePersist
    public void init() {
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Acl> getAcls() {
        return this.acls;
    }

    public void setAcls(Acl acl) {
        this.acls.add(acl);
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
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
        return "Space [id=" + id + ", title=" + title + ", createdAt=" + createdAt + ", updatedAt="
                + updatedAt + "]";
    }
}
