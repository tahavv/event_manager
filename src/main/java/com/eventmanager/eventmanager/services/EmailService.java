package com.eventmanager.eventmanager.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmailHtml(String to, String userName, String otpCode) throws IOException, MessagingException {
        String template = loadTemplate("otp-template.html");
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("{{userName}}", userName);
        placeholders.put("{{otpCode}}", otpCode);

        String processedHtml = replacePlaceholders(template, placeholders);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject("Your OTP Code - EventMaster");
        helper.setText(processedHtml, true);
        mailSender.send(mimeMessage);
    }

    public void sendResetPasswordEmailHtml(String to, String userName, String resetLink) throws IOException, MessagingException {
        String template = loadTemplate("reset-password-template.html");
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("{{userName}}", userName);
        placeholders.put("{{resetLink}}", resetLink);
        String processedHtml = replacePlaceholders(template, placeholders);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject("Reset Your Password - EventMaster");
        helper.setText(processedHtml, true);

        mailSender.send(mimeMessage);
    }

    public void sendWelcomeEmail(String to, String userName,String password) throws IOException, MessagingException {
        String template = loadTemplate("welcome.html");
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("{{userName}}", userName);
        placeholders.put("{{link}}", "http://localhost:4200/login");
        placeholders.put("{{password}}", password);
        String processedHtml = replacePlaceholders(template, placeholders);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject("Welcome to our platform - EventMaster");
        helper.setText(processedHtml, true);

        mailSender.send(mimeMessage);
    }

    private String loadTemplate(String templateName) throws IOException {
        ClassPathResource resource = new ClassPathResource("templates/" + templateName);
        byte[] bytes = Files.readAllBytes(resource.getFile().toPath());
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private String replacePlaceholders(String template, Map<String, String> placeholders) {
        String processed = template;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            processed = processed.replace(entry.getKey(), entry.getValue());
        }
        return processed;
    }
}
