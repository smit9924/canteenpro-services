package com.app.canteenpro.DataObjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class QRCodeDto {
    private String guid;
    private String name;
    private int number;
    private boolean selfServiceQRCode;
    private int capacity;
    private String qrImageURL;
}
