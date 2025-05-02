package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.Order;
import com.app.canteenpro.database.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Integer> {
    List<Order> findAllByUser(User user);
    Optional<Order> findByGuid(String guid);
}
