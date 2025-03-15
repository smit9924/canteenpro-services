package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemsRepo extends JpaRepository<Item, Integer> {
}
