package com.example.jart_cafe.api;

import com.example.jart_cafe.model.User;
import com.example.jart_cafe.repositories.UserRepository;
import com.example.jart_cafe.services.impl.PasswordResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
//@CrossOrigin(origins = "http://localhost:5173")
@RestController
//@CrossOrigin(origins = "https://jartcafe.com", allowCredentials = "true")
@RequestMapping("/api/password-reset")
public class PasswordRestController {
    @Autowired
    private PasswordResetService passwordResetService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/request")
    public ResponseEntity<?> resetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        User user = userRepository.findFirstByEmail(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        String token = passwordResetService.createPasswordResetTokenForUser(user);
        passwordResetService.sendPasswordResetEmail(email, token);
        return ResponseEntity.ok("Password reset email sent");
    }

    @PostMapping("/reset")
    public ResponseEntity<?> handlePasswordReset(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");
        User user = passwordResetService.validatePasswordResetToken(token);
        if (user == null) {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(newPassword)); // Ensure to encode the password
        userRepository.save(user);
        return ResponseEntity.ok("Password reset successful");
    }
}
