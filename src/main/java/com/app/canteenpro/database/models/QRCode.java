package com.app.canteenpro.database.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class QRCode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String guid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private int capacity;

    @ManyToOne
    @JoinColumn(name = "canteen_id")
    private Canteen canteen;

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

}
