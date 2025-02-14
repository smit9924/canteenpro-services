package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.UserLoginDto;
import com.app.canteenpro.DataObjects.UserRegistrationDto;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.database.repositories.UserRepo;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    public User signup(UserRegistrationDto registrationData) {
        User user = new User();
        user.setGuid(UUID.randomUUID().toString());
        user.setFirstname(registrationData.getFirstname());
        user.setLastname(registrationData.getLastname());
        user.setEmail(registrationData.getEmail());
        user.setPassword(passwordEncoder.encode(registrationData.getPassword()));
        userRepo.save(user);

        return user;
    }

    public User authentication(UserLoginDto loginData) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginData.getEmail(),
                        loginData.getPassword()
                )
        );

        User user = userRepo.findByEmail(loginData.getEmail()).orElseThrow();
        return user;
    }
}
