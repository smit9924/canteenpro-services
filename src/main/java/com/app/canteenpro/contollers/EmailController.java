package com.app.canteenpro.contollers;

import com.app.canteenpro.DataObjects.EmailDto;
import com.app.canteenpro.responses.ApiResponse;
import com.app.canteenpro.services.userapi.EmailService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/email")
@AllArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("send")
    public ResponseEntity<ApiResponse<?>> sendEmailWithoutAttachments(@RequestBody EmailDto emailDto) throws MessagingException {
        this.emailService.sendEmailWithoutAttachments(emailDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("sendwithattchments")
    public ResponseEntity<ApiResponse<?>> sendEmailWithAttachments(@RequestBody EmailDto emailDto) throws MessagingException {
        this.emailService.sendEmailWithAttchments(emailDto);
        ApiResponse<?> apiResponse = new ApiResponse<>(false, true, "", "");
        return ResponseEntity.ok(apiResponse);
    }
}
