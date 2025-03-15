package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.MediaMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaMetaDataRepo extends JpaRepository<MediaMetaData, Integer> {
    MediaMetaData findByGuid(String guid);
}
