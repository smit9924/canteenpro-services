package com.app.canteenpro.contollers;

import com.app.canteenpro.database.models.User;
import com.app.canteenpro.services.userapi.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class userController {
    @Autowired
    public UserService userService;

    // Usage: Get particular user data using GUID
    @GetMapping("")
    public String getUser(@RequestParam("guid") String userGUID) {
        return "returning particular user data";
    }

    // Usage: get list of all users preset in system
    @GetMapping("all")
    public String getAllUser() {
        return "Returning all user list";
    }

    // Usage: create new user
    @PostMapping("add")
    public String createUser() {
        System.out.println("Request arrived");
//        System.out.println(user);
        return "creating user";
    }

    // Usage: update existing user data
    @PostMapping("update")
    public String updateUser(@RequestParam("guid") String userGUID) {
        return "Updating user";
    }

    // Usage: delete the user if exists
    @PutMapping("delete")
    public String deleteUser(@RequestParam("guid") String userGUID) {
        return "deleting user";
    }
}
