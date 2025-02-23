package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.UserLoginDto;
import com.app.canteenpro.DataObjects.UserRegistrationDto;
import com.app.canteenpro.common.ApiResponse;
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
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<LoginResponse>> registration(@RequestBody UserRegistrationDto userRegistrationDto) {
        LoginResponse loginResponse = authService.signup(userRegistrationDto);
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<LoginResponse>(loginResponse, true, "","");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> authenticateuser(@RequestBody UserLoginDto userLoginDto) {
        LoginResponse loginResponse =  authService.authenticateUser(userLoginDto);
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<LoginResponse>(loginResponse, true, "","");
        return ResponseEntity.ok(apiResponse);
    }
}
