package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.UserLoginDto;
import com.app.canteenpro.DataObjects.UserRegistrationDto;
import com.app.canteenpro.common.LoginResponse;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.services.userapi.AuthService;
import com.app.canteenpro.services.userapi.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
    private final JwtService jwtService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<User> registration(@RequestBody UserRegistrationDto userRegistrationDto) {
        System.out.println("request arrived");
        User registeredUser = authService.signup(userRegistrationDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateuser(@RequestBody UserLoginDto userLoginDto) {
        System.out.println("inside login method");
        User authenticatedUser = authService.authentication(userLoginDto);
        String token = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);

        return ResponseEntity.ok(loginResponse);
    }
}
