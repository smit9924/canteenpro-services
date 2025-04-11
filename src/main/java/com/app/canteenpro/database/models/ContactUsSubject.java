package com.app.canteenpro.database.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contactUsSubject")
public class ContactUsSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false)
    private String guid;

    @Column(nullable = false)
    private String subject;
}
