package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo  extends JpaRepository<Address, Integer> {
}
