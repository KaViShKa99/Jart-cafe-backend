package com.example.jart_cafe.services.impl;

import com.example.jart_cafe.model.PasswordResetToken;
import com.example.jart_cafe.model.User;
import com.example.jart_cafe.repositories.PasswordResetTokenRepository;
import com.example.jart_cafe.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
        String url = "http://localhost:5173/password-reset?token=" + token;
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject("Password Reset Request");
        emailMessage.setText("Reset your password by clicking the link: " + url);
        mailSender.send(emailMessage);
    }

    public User validatePasswordResetToken(String token) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);
        if (resetToken == null || resetToken.getExpiryDate().before(new Date())) {
            return null;
        }
        return resetToken.getUser();
    }
}
