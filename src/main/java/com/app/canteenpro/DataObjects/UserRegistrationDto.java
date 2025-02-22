package com.app.canteenpro.DataObjects;

import lombok.Data;

@Data
public class UserRegistrationDto extends  UserLoginDto {
    private String firstname;
    private String lastname;
    private Integer roleLevel = 1;
}
