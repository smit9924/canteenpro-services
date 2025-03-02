package com.app.canteenpro.DataObjects;

import lombok.*;

import java.util.List;

@Getter
@Builder
@Data
public class EmailDto {
    private String recipient;
    private List<String> cc;
    private List<String> bcc;
    private String subject;
    private String msgBody;
    private String attachments;
    private boolean isReplay = false;
}
