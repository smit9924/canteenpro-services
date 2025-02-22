package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByGuid(String guid);
    Optional<User> findByEmail(String email);
}