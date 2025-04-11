package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Integer> {
}
