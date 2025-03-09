package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.RoleListDto;
import com.app.canteenpro.database.models.Roles;
import com.app.canteenpro.database.repositories.RolesRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RolesService {
    private final RolesRepo rolesRepo;

    public List<RoleListDto> getUserList() {
        final List<RoleListDto> roleList = rolesRepo.findAll().stream().map((Roles roles) -> {
            return RoleListDto.builder()
                    .role(roles.getRole())
                    .level(roles.getLevel())
                    .build();
        }).toList();

        return roleList;
    }
}
