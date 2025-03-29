package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.QRCodeDto;
import com.app.canteenpro.DataObjects.QRCodeListingDto;
import com.app.canteenpro.DataObjects.UpsertUserDto;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.services.userapi.QRCodeService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/qr")
public class QRCodeController {
    @Autowired
    QRCodeService qrCodeService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<?>> createQRCode(@RequestBody QRCodeDto qrCodeDto) {
        qrCodeService.createQR(qrCodeDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "New QR Code generated successfully!" ,"");
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<?>> updateQRCode(@RequestBody QRCodeDto qrCodeDto) {
        qrCodeService.updateQRCode(qrCodeDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "New QR Code generated successfully!" ,"");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<QRCodeDto>> getQRCode(@RequestParam String guid) throws WriterException, IOException {
        QRCodeDto qrCodeDto = qrCodeService.getQRCode(guid);
        ApiResponse<QRCodeDto> apiResponse = new ApiResponse<QRCodeDto>(qrCodeDto, true, "QR Code updated successfully!" ,"");
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponse<List<QRCodeListingDto>>> deleteQRCode(@RequestParam String guid) throws WriterException, IOException {
        qrCodeService.deleteQRCode(guid);
        List<QRCodeListingDto> qrCodeList = qrCodeService.getQRCodeList();
        ApiResponse<List<QRCodeListingDto>> apiResponse = new ApiResponse<List<QRCodeListingDto>>(qrCodeList, true, "QR Code updated successfully!" ,"");
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<QRCodeListingDto>>> getQRCodeList() throws WriterException, IOException {
        List<QRCodeListingDto> qrCodeList = qrCodeService.getQRCodeList();
        ApiResponse<List<QRCodeListingDto>> apiResponse = new ApiResponse<List<QRCodeListingDto>>(qrCodeList, true, "QR Code updated successfully!" ,"");
        return ResponseEntity.ok(apiResponse);
    }
}
