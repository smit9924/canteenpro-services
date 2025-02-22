package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.UserLoginDto;
import com.app.canteenpro.DataObjects.UserRegistrationDto;
import com.app.canteenpro.common.LoginResponse;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.services.userapi.AuthService;
import com.app.canteenpro.services.userapi.JwtService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<LoginResponse> registration(@RequestBody UserRegistrationDto userRegistrationDto) {
        LoginResponse loginResponse = authService.signup(userRegistrationDto);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateuser(@RequestBody UserLoginDto userLoginDto) {
        LoginResponse loginResponse =  authService.authenticateUser(userLoginDto);
        return ResponseEntity.ok(loginResponse);
    }
}
