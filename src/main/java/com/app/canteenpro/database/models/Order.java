package com.app.canteenpro.database.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "\"order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(unique = true)
    private String guid;

    @Column(nullable = true)
    private String instructions;

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
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "canteen_id")
    private Canteen canteen;
}
