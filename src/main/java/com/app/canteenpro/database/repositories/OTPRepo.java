package com.app.canteenpro.database.repositories;

import com.app.canteenpro.database.models.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepo extends JpaRepository<OTP, Integer> {
    OTP findByEmail(String email);
    Optional<OTP> findFirstByEmailOrderByCreatedOnDesc(String email);
}
