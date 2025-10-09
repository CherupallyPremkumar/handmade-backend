package com.homebase.admin.controller;

import com.homebase.admin.dto.AdminUserDTO;
import com.homebase.admin.dto.LoginRequest;
import com.homebase.admin.dto.LoginResponse;
import com.homebase.admin.security.JwtUtil;
import com.homebase.admin.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request,
            @RequestParam(required = false) String tenantId) {
        
        LoginResponse response = authService.login(request, tenantId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<AdminUserDTO> getCurrentUser(
            Authentication authentication,
            HttpServletRequest request) {
        String email = authentication.getName();
        String tenantId = request.getHeader("X-Tenant-ID");
        
        // Security: Require tenant ID - no default fallback
        if (tenantId == null || tenantId.isEmpty()) {
            return ResponseEntity.status(403).build();
        }
        
        AdminUserDTO user = authService.getCurrentUser(email, tenantId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<Map<String, Boolean>> verify2FA(
            @RequestBody Map<String, String> request) {
        
        String code = request.get("code");
        String sessionId = request.get("sessionId");
        
        // Mock 2FA verification - in production:
        // 1. Verify sessionId exists and is valid
        // 2. Verify code matches TOTP/SMS code for this session
        // 3. Mark session as 2FA-verified
        boolean success = "123456".equals(code) && sessionId != null && !sessionId.isEmpty();
        
        Map<String, Boolean> response = new HashMap<>();
        response.put("success", success);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/password-reset")
    public ResponseEntity<Map<String, String>> requestPasswordReset(
            @RequestBody Map<String, String> request) {
        
        String email = request.get("email");
        
        // Mock password reset - in production, send email with reset link
        Map<String, String> response = new HashMap<>();
        response.put("message", "Password reset email sent");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-session")
    public ResponseEntity<Map<String, Boolean>> verifySession(
            @RequestBody Map<String, String> request) {
        
        String sessionId = request.get("sessionId");
        
        // Mock session verification
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", sessionId != null && !sessionId.isEmpty());
        return ResponseEntity.ok(response);
    }
}
