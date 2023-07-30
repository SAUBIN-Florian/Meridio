package dev.florian.meridio.models;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class File {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String fileName;

    @NotNull
    private String type;

    @NotNull
    private String filePath;

    @ManyToOne()
    private Space space;

    @DateTimeFormat
    private LocalDateTime createdAt;

    @DateTimeFormat
    private LocalDateTime updatedAt = null;

    public File() {}

    public File(@NotNull String fileName, @NotNull String type, @NotNull String filePath) {
        this.fileName = fileName;
        this.type = type;
        this.filePath = filePath;
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Space getSpace() {
        return space;
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
        return "File [id=" + id + ", fileName=" + fileName + ", type=" + type + ", space=" + space + ", createdAt="
                + createdAt + "]";
    }   
}
