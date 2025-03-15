package com.app.canteenpro.database.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "foodCategory")
public class FoodCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String guid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private String description;

    private LocalDateTime createdOn;
    private LocalDateTime editedOn;

    // Auto set time stamp on creation
    @PrePersist
    protected void onCreate() {
        final LocalDateTime timestamp = LocalDateTime.now();
        createdOn = timestamp;
        editedOn = timestamp;
    }

    // Audio set timestamp on edit
    @PreUpdate
    protected void onUpdate() {
        editedOn = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "media_id")
    private MediaMetaData media;

    @ManyToOne
    @JoinColumn(name = "canteen_id")
    private Canteen canteen;
}
