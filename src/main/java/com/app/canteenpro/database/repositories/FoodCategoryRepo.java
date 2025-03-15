package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.Canteen;
import com.app.canteenpro.database.models.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodCategoryRepo extends JpaRepository<FoodCategory, Integer> {
    List<FoodCategory> findAllByCanteen(Canteen canteen);
    FoodCategory findByGuid(String guid);
}
