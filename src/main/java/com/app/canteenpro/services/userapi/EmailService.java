package com.app.canteenpro.services.userapi;

import com.app.canteenpro.DataObjects.EmailDto;
import com.app.canteenpro.exceptions.EmailTransmissionFailedException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    // Send EMAIL WITHOUT ATTACHMENTS
    public void sendEmailWithoutAttachments(EmailDto emailDto) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(this.sender);
        simpleMailMessage.setTo(emailDto.getRecipient());
        simpleMailMessage.setSubject(emailDto.getSubject());
        simpleMailMessage.setText(emailDto.getMsgBody());
        
        javaMailSender.send(simpleMailMessage);
    }

    // Send EMAIL HAVING ATTACHMENTS
    public void sendEmailWithAttchments(EmailDto emailDto) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        mimeMessageHelper.setFrom(this.sender);
        mimeMessageHelper.setTo(emailDto.getRecipient());
        mimeMessageHelper.setSubject(emailDto.getSubject());
        mimeMessageHelper.setText(emailDto.getMsgBody(), true);
        // mimeMessage.setContent(emailDto.getMsgBody(), "text/html");
        mimeMessage.setHeader("Message-ID", UUID.randomUUID().toString());
        System.out.println("Message id: " + UUID.randomUUID().toString());
        mimeMessage.setHeader("References", "");
        mimeMessage.setHeader("In-Reply-To", "");


        // TODO: Implement functionality to attach file

        javaMailSender.send(mimeMessage);
    }
}
