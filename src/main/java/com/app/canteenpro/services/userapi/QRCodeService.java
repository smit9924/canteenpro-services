package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.MediaDataDto;
import com.app.canteenpro.DataObjects.QRCodeDto;
import com.app.canteenpro.DataObjects.QRCodeListingDto;
import com.app.canteenpro.common.Enums;
import com.app.canteenpro.database.models.MediaMetaData;
import com.app.canteenpro.database.models.QRCode;
import com.app.canteenpro.database.models.User;
import com.app.canteenpro.database.repositories.MediaMetaDataRepo;
import com.app.canteenpro.database.repositories.QRCodeRepo;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class QRCodeService {

    @Autowired
    private QRCodeRepo qrCodeRepo;

    @Autowired
    private FirestorageService firestorageService;

    @Autowired
    private MediaMetaDataRepo mediaMetaDataRepo;

    @Autowired
    private CommonService commonService;

    public String getQRCodeImageBase64(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageConfig con = new MatrixToImageConfig( 0xFF000002 , 0xFFFFC041 ) ;

        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream,con);
        byte[] pngData = pngOutputStream.toByteArray();

        String base64String = Base64.getEncoder().encodeToString(pngData);
        base64String = "data:image/png;base64," + base64String;

        return base64String;
    }

    public void createQR(QRCodeDto qrCodeDto) {
        User currentUser = commonService.getLoggedInUser();

        int qrCodeType = 0;
        if(qrCodeDto.isSelfServiceQRCode()) {
            qrCodeType = Enums.QR_CODE_TYPE.SELF_SERVICE_ORDER_QR.getValue();
        } else {
            qrCodeType = Enums.QR_CODE_TYPE.TABLE_ORDER_QR.getValue();
        }

        QRCode qrCode = new QRCode();
        qrCode.setGuid(UUID.randomUUID().toString());
        qrCode.setName(qrCodeDto.getName());
        qrCode.setCapacity(qrCodeDto.getCapacity());
        qrCode.setNumber(qrCodeDto.getNumber());
        qrCode.setType(qrCodeType);
        qrCode.setCanteen(currentUser.getCanteen());
        qrCodeRepo.save(qrCode);
    }

    public void updateQRCode(QRCodeDto qrCodeDto) {
        int qrCodeType = 0;
        if(qrCodeDto.isSelfServiceQRCode()) {
            qrCodeType = Enums.QR_CODE_TYPE.SELF_SERVICE_ORDER_QR.getValue();
        } else {
            qrCodeType = Enums.QR_CODE_TYPE.TABLE_ORDER_QR.getValue();
        }

        QRCode qrCode = qrCodeRepo.findByGuid(qrCodeDto.getGuid());
        qrCode.setName(qrCodeDto.getName());
        qrCode.setCapacity(qrCodeDto.getCapacity());
        qrCode.setNumber(qrCodeDto.getNumber());
        qrCode.setType(qrCodeType);
        qrCodeRepo.save(qrCode);
    }

    public QRCodeDto getQRCode(String guid) throws WriterException, IOException {
        QRCode qrCode = qrCodeRepo.findByGuid(guid);

        boolean selfServiceQRCode;
        if(qrCode.getType() == Enums.QR_CODE_TYPE.SELF_SERVICE_ORDER_QR.getValue()) {
            selfServiceQRCode = true;
        } else {
            selfServiceQRCode = false;
        }

        String generatedQRCode = this.getQRCodeImageBase64("Add URL here" , 150, 150);

        QRCodeDto qrCodeDto = QRCodeDto.builder()
                .guid(qrCode.getGuid())
                .name(qrCode.getName())
                .selfServiceQRCode(selfServiceQRCode)
                .capacity(qrCode.getCapacity())
                .number(qrCode.getNumber())
                .qrImageURL(generatedQRCode)
                .build();

        return qrCodeDto;
    }

    public List<QRCodeListingDto> getQRCodeList() throws WriterException, IOException {
        User currentUser = commonService.getLoggedInUser();
        List<QRCode> fetchedQRCodes = qrCodeRepo.findAllByCanteen(currentUser.getCanteen());

        List<QRCodeListingDto> qrCodes = fetchedQRCodes.stream()
                .map((qrCode)  -> {
                    try {
                        boolean selfServiceQRCode;
                        if(qrCode.getType() == Enums.QR_CODE_TYPE.SELF_SERVICE_ORDER_QR.getValue()) {
                            selfServiceQRCode = true;
                        } else {
                            selfServiceQRCode = false;
                        }

                        String generatedQRCode = this.getQRCodeImageBase64("Add URL here" , 150, 150);

                        QRCodeListingDto qrCodeListingDto = new QRCodeListingDto(
                                qrCode.getGuid(),
                                qrCode.getName(),
                                qrCode.getNumber(),
                                selfServiceQRCode,
                                qrCode.getCapacity(),
                                generatedQRCode,
                                qrCode.getCreatedOn(),
                                qrCode.getEditedOn()
                        );

                        return qrCodeListingDto;
                    } catch (WriterException | IOException e){
                        throw new RuntimeException("Error generating QR Code for: " + qrCode.getName(), e);
                    }
                })
                .toList();

        return qrCodes;
    }

    public void deleteQRCode(String guid) {
        QRCode qrCode = qrCodeRepo.findByGuid(guid);
        qrCodeRepo.delete(qrCode);
    }
}
