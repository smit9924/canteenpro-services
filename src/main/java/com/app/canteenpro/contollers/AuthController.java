package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.RoleListDto;
import com.app.canteenpro.DataObjects.UserLoginDto;
import com.app.canteenpro.DataObjects.UserRegistrationDto;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.responses.LoginResponse;
import com.app.canteenpro.database.models.Roles;
import com.app.canteenpro.services.userapi.AuthService;
import com.app.canteenpro.services.userapi.RolesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/roles")
    public ResponseEntity<ApiResponse<List<RoleListDto>>> userRolesList() {
        ApiResponse<List<RoleListDto>> apiResponse = new ApiResponse<List<RoleListDto>>(rolesService.getUserList(), true, "","");
        return ResponseEntity.ok(apiResponse);
    }
}
