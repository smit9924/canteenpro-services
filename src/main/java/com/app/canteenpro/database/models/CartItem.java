package com.app.canteenpro.database.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "cart")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "food_item_id", nullable = false)
    private FoodItem foodItem;

    @Column(nullable = false)
    private Integer quantity;

    private LocalDateTime addedOn;
    private LocalDateTime updatedOn;

    // Auto set time stamp on creation
    @PrePersist
    protected void onCreate() {
        final LocalDateTime timestamp = LocalDateTime.now();
        addedOn = timestamp;
        updatedOn = timestamp;
    }

    // Audio set timestamp on edit
    @PreUpdate
    protected void onUpdate() {
        updatedOn = LocalDateTime.now();
    }
}
