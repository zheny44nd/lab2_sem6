package org.example.lab2.controller;

import jakarta.validation.Valid;
import org.example.lab2.dto.ApiResponse;
import org.example.lab2.dto.AuthRequest;
import org.example.lab2.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody @Valid AuthRequest req, @RequestParam(defaultValue = "STUDENT") String role) {
        authService.register(req, role);
        return ApiResponse.success("Registered successfully");
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody @Valid AuthRequest req) {
        String token = authService.login(req);
        return ApiResponse.success(token);
    }
}
