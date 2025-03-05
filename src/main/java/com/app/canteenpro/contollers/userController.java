package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.UpsertUserDto;
import com.app.canteenpro.DataObjects.UserListingDto;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.services.userapi.UserService;
import com.app.canteenpro.exceptions.InSufficientParameterDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.InsufficientResourcesException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class userController {
    @Autowired
    public UserService userService;

    // Create user: Manager
    @PostMapping("/create/manager")
    public ResponseEntity<ApiResponse<?>> createManager(@RequestBody UpsertUserDto upsertUserDto) throws IOException {
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "New manager created successfully!" ,"");
        userService.createManager(upsertUserDto);
        return ResponseEntity.ok(apiResponse);
    }

    // get user data
    @GetMapping("")
    public ResponseEntity<ApiResponse<UpsertUserDto>> getUserData(@RequestParam String guid) {
        UpsertUserDto upsertUserDto = userService.getUserData(guid);
        ApiResponse<UpsertUserDto> apiResponse = new ApiResponse<UpsertUserDto>(upsertUserDto, true, "New manager created successfully!" ,"");
        return ResponseEntity.ok(apiResponse);
    }

    // delete user
    @DeleteMapping("")
    public ResponseEntity<ApiResponse<List<UserListingDto>>> deleteUser(@RequestParam String guid) {
        List<UserListingDto> userListing = userService.deleteUser(guid);
        ApiResponse<List<UserListingDto>> apiResponse = new ApiResponse<List<UserListingDto>>(userListing, true, "New manager created successfully!" ,"");
        return ResponseEntity.ok(apiResponse);
    }

    // update user
    @PutMapping("")
    public ResponseEntity<ApiResponse<?>> updateUser(@RequestBody UpsertUserDto upsertUserDto) {
        userService.updateUser(upsertUserDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "User updated successfully!" ,"");
        return ResponseEntity.ok(apiResponse);
    }

    // Get user listing
    @GetMapping("/list/{userType}")
    public ResponseEntity<ApiResponse<List<UserListingDto>>> getUserList(@PathVariable String userType) {
        List<UserListingDto> usersList = userService.getUserList(userType);
        ApiResponse<List<UserListingDto>> apiResponse = new ApiResponse<List<UserListingDto>>(usersList, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }
}
