package com.app.canteenpro.DataObjects;

import lombok.Data;

@Data
public class UserRegistrationDto {
    private String email;
    private String password;
    private String firstname;
    private String lastname;
}
