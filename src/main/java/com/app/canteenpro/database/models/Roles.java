package com.app.canteenpro.database.models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@Table(name = "roles")
public class Roles implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String role;

    @Column(unique = true, nullable = false)
    private Integer level;

    @Override
    public String getAuthority() {
        return this.getLevel().toString();
    }
}
