package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.Canteen;
import com.app.canteenpro.database.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CanteenRepo  extends JpaRepository<Canteen, Integer> {
        Optional<Canteen> findByGuid(String guid);
}
