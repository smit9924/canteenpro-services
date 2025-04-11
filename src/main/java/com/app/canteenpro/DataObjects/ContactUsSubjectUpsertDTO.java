package com.app.canteenpro.DataObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ContactUsSubjectUpsertDTO {
    private String guid;
    private String subject;
}
