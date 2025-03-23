package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.CartItem;
import com.app.canteenpro.database.models.FoodItem;
import com.app.canteenpro.database.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
    List<CartItem> findAllByUser(User user);
    CartItem findByUserAndFoodItem(User user, FoodItem foodItem);
}
