package com.app.canteenpro.database.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "canteen")
public class Canteen {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(unique = true)
    private String guid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer type;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
