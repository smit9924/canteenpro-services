package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.UpsertUserDto;
import com.app.canteenpro.DataObjects.UserListingDto;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.responses.UserProfileResponse;
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
    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> createManager(@RequestBody UpsertUserDto upsertUserDto) throws IOException {
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "New manager created successfully!" ,"");
        userService.createUser(upsertUserDto);
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

    // Get user profile data
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserProfileResponse>> getUserProfileData() {
        final UserProfileResponse userProfileData = userService.getUserProfileData();
        System.out.println(userProfileData);
        ApiResponse<UserProfileResponse> apiResponse = new ApiResponse<UserProfileResponse>(userProfileData, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }
}
