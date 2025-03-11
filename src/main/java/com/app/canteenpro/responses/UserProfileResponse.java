package com.app.canteenpro.responses;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserProfileResponse {
    private String firstname;
    private String lastname;
    private String email;
    private String guid;
    private boolean defaultPasswordUpdated = false;
    private Integer roleLevel;
}
