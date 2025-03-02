package com.app.canteenpro.DataObjects;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateUserDto {
    private String firstname;
    private String lastname;
    private String email;
}
