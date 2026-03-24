package com.codeb.mis.service;

import com.codeb.mis.config.JwtUtil;
import com.codeb.mis.dto.LoginRequest;
import com.codeb.mis.dto.LoginResponse;
import com.codeb.mis.dto.RegisterRequest;
import com.codeb.mis.model.User;
import com.codeb.mis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired(required = false)
    private JavaMailSender mailSender;

    private final Map<String, String> resetTokens = new HashMap<>();

    public ResponseEntity<?> register(RegisterRequest request) {
        if (request.getFullName() == null || request.getFullName().isEmpty() ||
            request.getEmail() == null || request.getEmail().isEmpty() ||
            request.getPassword() == null || request.getPassword().isEmpty() ||
            request.getRole() == null || request.getRole().isEmpty()) {
            return ResponseEntity.badRequest().body("All fields are required.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }
        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setStatus(User.Status.active);
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    public ResponseEntity<?> login(LoginRequest request) {
        if (request.getEmail() == null || request.getPassword() == null) {
            return ResponseEntity.badRequest().body("Email and password are required.");
        }
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid email or password.");
        }
        User user = userOpt.get();
        if (user.getStatus() == User.Status.inactive) {
            return ResponseEntity.badRequest().body("Account is inactive.");
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.badRequest().body("Invalid email or password.");
        }
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(new LoginResponse(token, user.getRole(), user.getFullName(), user.getEmail()));
    }

    public ResponseEntity<?> forgotPassword(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Email not found.");
        }
        String token = UUID.randomUUID().toString();
        resetTokens.put(token, email);
        if (mailSender != null) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset - MIS System");
            message.setText("Reset your password using this token: " + token);
            mailSender.send(message);
        }
        return ResponseEntity.ok("Password reset link sent to your email.");
    }

    public ResponseEntity<?> resetPassword(String token, String newPassword) {
        String email = resetTokens.get(token);
        if (email == null) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found.");
        }
        User user = userOpt.get();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        resetTokens.remove(token);
        return ResponseEntity.ok("Password reset successfully.");
    }

    public ResponseEntity<?> logout(String token) {
        return ResponseEntity.ok("Logged out successfully.");
    }
}
