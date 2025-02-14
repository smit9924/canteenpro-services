package com.app.canteenpro.services.userapi;

import com.app.canteenpro.database.models.User;
import com.app.canteenpro.database.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    public User getUser(int id) {
        if(userRepo.findById(id).isPresent()) {
            return userRepo.findById(id).get();
        }
        return new User();
    }

    public List<User> getAllUser() {
        return userRepo.findAll();
    }

    public void addUser(User user) {
        userRepo.save(user);
    }
}
