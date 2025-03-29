package com.app.canteenpro.DataObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class QRCodeListingDto {
    private String guid;
    private String name;
    private int number;
    private boolean selfServiceQRCode;
    private int capacity;
    private String qrImageURL;
    private String dateCreated;
    private String dateEdited;

    public QRCodeListingDto(
            String guid,
            String name,
            int number,
            boolean selfServiceQRCode,
            int capacity,
            String qrImageURL,
            LocalDateTime dateCreated,
            LocalDateTime dateEdited
    ) {
        this.guid = guid;
        this.name = name;
        this.number = number;
        this.selfServiceQRCode = selfServiceQRCode;
        this.capacity = capacity;
        this.qrImageURL = qrImageURL;
        this.dateCreated = this.formatDate(dateCreated);
        this.dateEdited = this.formatDate(dateEdited);
    }

    private String formatDate(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");  // Omitting seconds
        return dateTime.format(formatter);
    }
}
