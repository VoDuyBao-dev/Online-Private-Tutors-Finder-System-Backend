package com.example.tutorsFinderSystem.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Async
    public void sendOtpEmail(String to, String otp) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(to);
            helper.setSubject("Your OTP Code");

            Context context = new Context();
            context.setVariable("otp", otp);
            context.setVariable("appName", "ETC System");

            String htmlContent = templateEngine.process("email/otp_email", context);
            helper.setText(htmlContent, true);

            mailSender.send(mimeMessage);
            log.info("Gửi email OTP thành công đến: {}", to);

        } catch (Exception e) {

            log.error("Lỗi khi gửi email OTP đến {}: ", to, e);

        }
    }
}
