package com.app.canteenpro.database.models;

import com.app.canteenpro.common.Enums;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(unique = true)
    private String guid;

    @Column(nullable = true)
    private String instructions;

    @Column(nullable = false)
    private Enums.ORDER_STATUS orderStatus = Enums.ORDER_STATUS.NOT_PLACED;

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

    @ManyToMany(mappedBy = "order")
    private Collection<OrderItem> orderItems = new ArrayList<>();
}
