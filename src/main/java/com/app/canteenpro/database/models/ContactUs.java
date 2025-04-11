package com.app.canteenpro.database.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contactUs")
public class ContactUs {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String guid;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String countryCode;

    @Column(nullable = true)
    private String contactNo;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private ContactUsSubject subject;


    @Column(nullable = true)
    private String customSubject;

    @Column(nullable = false)
    private String message;
}
