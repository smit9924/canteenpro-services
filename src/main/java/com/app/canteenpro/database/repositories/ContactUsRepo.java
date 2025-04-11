package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.ContactUs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactUsRepo  extends JpaRepository<ContactUs, Integer> {
}
