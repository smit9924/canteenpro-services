package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.CreateUserDto;
import com.app.canteenpro.DataObjects.EmailDto;
import com.app.canteenpro.common.ApiResponse;
import com.app.canteenpro.common.Enums;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.database.repositories.RolesRepo;
import com.app.canteenpro.database.repositories.UserRepo;
import com.app.canteenpro.exceptions.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.app.canteenpro.common.appConstants;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static com.app.canteenpro.common.appConstants.INTERNAL_API_EMAIL_WITH_MIME;

@Service
@AllArgsConstructor
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    RolesRepo rolesRepo;

    @Autowired
    private RestTemplate restTemplate;

    private final PasswordEncoder passwordEncoder;

    public User getUser(int id) {
        if(userRepo.findById(id).isPresent()) {
            return userRepo.findById(id).get();
        }
        return new User();
    }

    private String generatePassword(Integer passwordLength) {
        final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*!";
        Random random = new SecureRandom();
        StringBuilder password = new StringBuilder(passwordLength);
        for (int i = 0; i < passwordLength; i++) {
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

    public User createManager(CreateUserDto createUserDto) throws IOException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            final String loggedInUserEmail = authentication.getName();
            User loggedInUser = userRepo.findByEmail(loggedInUserEmail).orElseThrow();
            String generatedPassword = this.generatePassword(8);

            User manager = new User();
            manager.setGuid(UUID.randomUUID().toString());
            manager.setFirstname(createUserDto.getFirstname());
            manager.setLastname(createUserDto.getLastname());
            manager.setEmail(createUserDto.getEmail());
            manager.setRole(rolesRepo.findByLevel(Enums.USER_ROLES.MANAGER.getValue()));
            manager.setCanteen(loggedInUser.getCanteen());
            manager.setPassword(passwordEncoder.encode(generatedPassword));
            userRepo.save(manager);

            // Get email template and set data
            String emailTemplate = Files.readString(Paths.get("src/main/resources/templates/initial-login-credential.html"));
            emailTemplate = emailTemplate.replace("{{firstname}}", manager.getFirstname())
                    .replace("{{accountType}}", manager.getRole().getRole())
                    .replace("{{email}}", manager.getEmail())
                    .replace("{{password}}", generatedPassword)
                    .replace("{{loginUrl}}", appConstants.ACCOUNT_LOGIN_URL);

            // Send credentials to user via email
            EmailDto emailDto = EmailDto.builder()
                            .recipient(createUserDto.getEmail())
                            .subject(appConstants.INITIAL_LOGIN_CREDENTIALS_EMAIL_SUBJECT)
                            .msgBody(emailTemplate)
                            .build();
            restTemplate.postForEntity(appConstants.INTERNAL_API_EMAIL_WITH_MIME, emailDto, ApiResponse.class);

            return manager;
        } else {
            throw new UserNotFoundException("Signed in user not found! Try to Re-login and try again!");
        }

    }
}
