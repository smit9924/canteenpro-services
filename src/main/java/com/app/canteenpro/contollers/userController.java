package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.CreateUserDto;
import com.app.canteenpro.DataObjects.UserListingDto;
import com.app.canteenpro.common.Enums;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.services.userapi.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class userController {
    @Autowired
    public UserService userService;

    // Create user: Manager
    @PostMapping("/create/manager")
    public ResponseEntity<ApiResponse<?>> createManager(@RequestBody CreateUserDto createUserDto) throws IOException {
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "New manager created successfully!" ,"");
        userService.createManager(createUserDto);
        return ResponseEntity.ok(apiResponse);
    }

    // Get user listing
    @GetMapping("/{userType}")
    public ResponseEntity<ApiResponse<List<UserListingDto>>> getUsers(@PathVariable String userType) {
        List<UserListingDto> usersList = userService.getUserList(userType);
        ApiResponse<List<UserListingDto>> apiResponse = new ApiResponse<List<UserListingDto>>(usersList, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }
}
