package com.ecomarketspa.EcoMarketSPA.Service.email;

import com.ecomarketspa.EcoMarketSPA.Dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(EmailDto emailDto) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setTo(emailDto.getTo());
            helper.setSubject(emailDto.getSubject());
            helper.setText(emailDto.getBody(), true); // true = HTML

            mailSender.send(mimeMessage);
            log.info("Email enviado exitosamente a: {}", emailDto.getTo());
        } catch (MessagingException e) {
            log.error("Error enviando email: {}", e.getMessage());
            throw new RuntimeException("Error enviando email", e);
        }
    }
}