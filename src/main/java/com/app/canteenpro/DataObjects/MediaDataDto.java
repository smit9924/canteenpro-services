package com.app.canteenpro.DataObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class MediaDataDto {
    private String guid;
    private String fileName;
    private String extension;
    private LocalDateTime initiallyUploadedOn;
    private LocalDateTime latestUploadOn;
}
