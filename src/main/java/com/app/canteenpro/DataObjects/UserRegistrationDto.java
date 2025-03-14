package com.app.canteenpro.DataObjects;

import lombok.Data;

@Data
public class UserRegistrationDto extends UserLoginDto {
    private String firstname;
    private String lastname;
    private Integer roleLevel;

    // Canteeen details
    private String canteenName;
    private Integer canteenType;

    // Canteen address details
    private String shop;
    private String apartment;
    private String Area;
    private String Landmark;
    private String city;
    private String state;
    private String country;
    private String zipcode;
}
