package com.example.jart_cafe.services.impl;

import com.example.jart_cafe.model.PasswordResetToken;
import com.example.jart_cafe.model.User;
import com.example.jart_cafe.repositories.PasswordResetTokenRepository;
import com.example.jart_cafe.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PasswordResetService {
    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${frontend.baseurl}")
    private String frontendBaseUrl;


    public String createPasswordResetTokenForUser(User user) {
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(new Date(System.currentTimeMillis() + 3600 * 1000)); // 1 hour expiry
        tokenRepository.save(resetToken);
        return token;
    }

    public void sendPasswordResetEmail(String email, String token) {
        String url = frontendBaseUrl+"/password-reset?token=" + token;

//        SimpleMailMessage emailMessage = new SimpleMailMessage();
//        emailMessage.setTo(email);
//        emailMessage.setSubject("Password Reset Request");
//        emailMessage.setText("Reset your password by clicking the link: " + url);
//        mailSender.send(emailMessage);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String htmlMsg = "<div style='font-family: Arial, sans-serif; color: #333;'>"
                + "<h2 style='color: #444;'>Password Reset Request</h2>"
                + "<p>We received a request to reset your password. Click the button below to reset it:</p>"
                + "<a href='" + url + "' style='display: inline-block; padding: 10px 20px; margin: 20px 0; background-color: #007bff; color: white; text-decoration: none; border-radius: 5px;'>Reset Password</a>"
                + "<p>If you didn't request a password reset, please ignore this email.</p>"
                + "<p>Thanks,<br>Jart-cafe</p>"
                + "</div>";

        try {
            helper.setTo(email);
            helper.setSubject("Password Reset Request");
            helper.setText(htmlMsg, true); // Set to true for HTML
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle the exception (e.g., log it or rethrow it)
            e.printStackTrace();
        }
    }

    public User validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpiryDate().before(new Date())) {
            return null;
        }
        return resetToken.getUser();
    }
}
