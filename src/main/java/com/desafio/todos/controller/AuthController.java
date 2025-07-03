package com.desafio.todos.controller;


import com.desafio.todos.config.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final JwtService jwtService;
    
    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    
    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> getToken() {
        String token = jwtService.generateToken("api-user");
        return ResponseEntity.ok(Map.of("token", token));
    }
}