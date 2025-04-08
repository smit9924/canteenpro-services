package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.RoleListDto;
import com.app.canteenpro.DataObjects.UserLoginDto;
import com.app.canteenpro.DataObjects.UserRegistrationDto;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.responses.LoginResponse;
import com.app.canteenpro.responses.UserProfileResponse;
import com.app.canteenpro.services.userapi.AuthService;
import com.app.canteenpro.services.userapi.RolesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RolesService rolesService;

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

    // update default password
    // return: user profile data;
    @PostMapping("/changedefaultpassword")
    public ResponseEntity<ApiResponse<UserProfileResponse>> changeDefaultPassword(@RequestBody String password) {
        final UserProfileResponse userProfileData = authService.changeDefaultPassword(password);
        ApiResponse<UserProfileResponse> apiResponse = new ApiResponse<UserProfileResponse>(userProfileData, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<RoleListDto>>> userRolesList() {
        ApiResponse<List<RoleListDto>> apiResponse = new ApiResponse<List<RoleListDto>>(rolesService.getUserList(), true, "","");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/customer/sendotp")
    public ResponseEntity<ApiResponse<?>> signupCustomer(@RequestBody String email) throws IOException {
        authService.generateAndSendOTP(email);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "","");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/customer/login")
    public ResponseEntity<ApiResponse<LoginResponse>> authenticateCustomer(@RequestBody UserLoginDto userLoginDto) {
        LoginResponse loginResponse =  authService.loginCustomUser(userLoginDto);
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<LoginResponse>(loginResponse, true, "","");
        return ResponseEntity.ok(apiResponse);
    }

}
