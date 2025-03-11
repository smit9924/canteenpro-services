package com.app.canteenpro.services.userapi;

import com.app.canteenpro.database.models.User;
import com.app.canteenpro.database.repositories.UserRepo;
import com.app.canteenpro.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommonService {
    private final UserRepo userRepo;

    public User getLoggedInUser() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            final String loggedInUserEmail = authentication.getName();
            final User loggedinUser = userRepo.findByEmail(loggedInUserEmail).orElseThrow();
            return loggedinUser;
        } else {
            throw new UserNotFoundException("Signed in user not found! Try to Re-login and try again!");
        }
    }
}
