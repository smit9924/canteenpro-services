package com.app.canteenpro.DataObjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@AllArgsConstructor
@Data
@Builder
public class UpsertUserDto {
    private String guid = null;
    private Integer userType = null;
    private String firstname;
    private String lastname;
    private String email;
}
