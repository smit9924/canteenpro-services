package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.Canteen;
import com.app.canteenpro.database.models.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodItemRepo extends JpaRepository<FoodItem, Integer> {
    FoodItem findByGuid(String guid);
    List<FoodItem> findAllByCanteen(Canteen canteen);
}
