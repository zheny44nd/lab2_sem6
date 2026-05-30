package org.example.lab2.service;

import org.example.lab2.dto.AuthRequest;
import org.example.lab2.entity.AppUser;
import org.example.lab2.repository.AppUserRepository;
import org.example.lab2.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
public class AuthService {
    private final AppUserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    private static final Set<String> ALLOWED_ROLES = Set.of("STUDENT", "LECTOR", "ADMIN");

    public AuthService(AppUserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(AuthRequest req, String role) {
        if (!ALLOWED_ROLES.contains(role)) {
            throw new org.example.lab2.exception.BadRequestException("Invalid role");
        }
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new org.example.lab2.exception.BadRequestException("User already exists");
        }
        AppUser user = new AppUser();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(role);
        userRepository.save(user);
    }

    public String login(AuthRequest req) {
        AppUser user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new org.example.lab2.exception.UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new org.example.lab2.exception.UnauthorizedException("Invalid credentials");
        }

        return jwtUtil.generateToken(user.getUsername(), user.getRole());
    }
}
