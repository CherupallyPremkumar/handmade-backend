package com.homebase.admin.controller.user;


import com.homebase.admin.dto.user.UserLoginRequest;
import com.homebase.admin.dto.user.UserLoginResponse;
import com.homebase.admin.security.JwtUtil;
import com.homebase.admin.service.AuthService;
import com.homebase.admin.service.user.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user/auth")
public class UserAuthController {

    @Autowired
    private AuthUserService authUserService;
    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        UserLoginResponse response = authUserService.login(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-token")
    public ResponseEntity<Map<String, Object>> verifyToken(
            @RequestHeader("Authorization") String authorizationHeader) {

        Map<String, Object> response = new HashMap<>();

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.put("valid", false);
            response.put("message", "Missing or invalid Authorization header");
            return ResponseEntity.badRequest().body(response);
        }

        String token = authorizationHeader.substring(7);

        try {
            // 1. Validate JWT signature and expiration
            boolean valid = jwtUtil.validateToken(token, jwtUtil.extractUsername(token));
            // 2. Optional: fetch user info if needed
            if (valid) {
                response.put("valid", true);
            } else {
                response.put("valid", false);
                response.put("message", "Invalid or expired token");
            }
        } catch (Exception e) {
            response.put("valid", false);
            response.put("message", "Token verification failed: " + e.getMessage());
        }

        return ResponseEntity.ok(response);
    }
}
