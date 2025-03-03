package com.app.canteenpro.DataObjects;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class UserListingDto {
    private String guid;
    private String email;
    private String firstname;
    private String lastname;
    private String dateCreated;
    private String dateEdited;

    public UserListingDto(
            String guid,
            String email,
            String firstname,
            String lastname,
            LocalDateTime dateCreated,
            LocalDateTime dateEdited) {
        this.guid = guid;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateCreated = this.formatDate(dateCreated);
        this.dateEdited = this.formatDate(dateEdited);
    }

    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");  // Omitting seconds
        return dateTime.format(formatter);
    }
}
