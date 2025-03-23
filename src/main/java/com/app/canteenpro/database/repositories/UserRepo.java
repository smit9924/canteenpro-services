package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.Canteen;
import com.app.canteenpro.database.models.Roles;
import com.app.canteenpro.database.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User, Integer> {
    User findByGuid(String guid);
    Optional<User> findByEmail(String email);
    List<User> findAllByRole(Roles role);
    List<User> findAllByRoleAndCanteen(Roles role, Canteen canteen);
}