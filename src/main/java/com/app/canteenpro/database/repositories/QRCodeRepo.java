package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.Canteen;
import com.app.canteenpro.database.models.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QRCodeRepo extends JpaRepository<QRCode, Integer> {
    QRCode findByGuid(String guid);
    List<QRCode> findAllByCanteen(Canteen canteen);
}
