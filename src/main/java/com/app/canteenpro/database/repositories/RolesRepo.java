package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepo extends JpaRepository<Roles, Integer> {
    Roles findByLevel(Integer level);
}
