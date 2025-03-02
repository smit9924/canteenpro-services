package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.CreateUserDto;
import com.app.canteenpro.common.ApiResponse;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.services.userapi.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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
}
