package com.app.canteenpro.DataObjects;

import lombok.*;

@Builder
@Getter
@Setter
public class ContactUsUpsertDto {
    private String firstName;
    private String lastname;
    private String email;
    private String countryCode;
    private String contactNo;
    private ContactUsSubjectUpsertDTO subject;
    private String message;
    private String customSubject;
}
