package com.app.canteenpro.database.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "MediaMetaData")
public class MediaMetaData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String guid;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String extension;

    private LocalDateTime initiallyUploadedOn;
    private LocalDateTime latestUploadedOn;

    // Auto set time stamp on creation
    @PrePersist
    protected void onCreate() {
        final LocalDateTime timestamp = LocalDateTime.now();
        initiallyUploadedOn = timestamp;
        latestUploadedOn = timestamp;
    }

    // Audio set timestamp on edit
    @PreUpdate
    protected void onUpdate() {
        latestUploadedOn = LocalDateTime.now();
    }
}
