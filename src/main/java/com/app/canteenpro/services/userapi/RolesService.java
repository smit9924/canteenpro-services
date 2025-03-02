package com.app.canteenpro.services.userapi;

import com.app.canteenpro.database.models.Roles;
import com.app.canteenpro.database.repositories.RolesRepo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RolesService {
    private final RolesRepo rolesRepo;

    public List<Roles> getUserList() {
        return rolesRepo.findAll();
    }
}
