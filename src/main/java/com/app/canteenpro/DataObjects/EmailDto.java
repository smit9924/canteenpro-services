package com.app.canteenpro.DataObjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
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
