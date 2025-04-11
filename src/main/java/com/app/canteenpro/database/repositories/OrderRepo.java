package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Integer> {
}
