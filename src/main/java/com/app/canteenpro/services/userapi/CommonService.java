package com.app.canteenpro.services.userapi;

import com.app.canteenpro.database.models.User;
import com.app.canteenpro.database.repositories.UserRepo;
import com.app.canteenpro.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@AllArgsConstructor
public class CommonService {
    private final UserRepo userRepo;

    public User getLoggedInUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.isAuthenticated()) {
            final String loggedInUserEmail = authentication.getName();
            final User loggedinUser = userRepo.findByEmail(loggedInUserEmail).orElseThrow();
            return loggedinUser;
        } else {
            throw new UserNotFoundException("Signed in user not found! Try to Re-login and try again!");
        }
    }

    public String generatePassword(Integer passwordLength, boolean otp) {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*!";
        final String OTP_CHARACTER = "0123456789";
        String charString = "";

        if(otp) {
            charString = OTP_CHARACTER;
        } else {
            charString = CHARACTERS;
        }

        Random random = new SecureRandom();
        StringBuilder password = new StringBuilder(passwordLength);
        for (int i = 0; i < passwordLength; i++) {
            password.append(charString.charAt(random.nextInt(charString.length())));
        }
        return password.toString();
    }
}
