package com.homebase.admin.service;

import com.homebase.admin.dto.AdminUserDTO;
import com.homebase.admin.dto.LoginRequest;
import com.homebase.admin.dto.LoginResponse;
import com.homebase.admin.dto.TenantConfigDTO;
import com.homebase.admin.entity.AdminUser;
import com.homebase.admin.repository.AdminUserRepository;
import com.homebase.admin.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Mock tenant configurations (in production, store in database)
    private static final Map<String, TenantConfigDTO> TENANT_CONFIGS = new HashMap<>();

    static {
        TENANT_CONFIGS.put("tenant1", new TenantConfigDTO.Builder()
                .id("tenant1")
                .name("Tenant 1 Store")
                .logoUrl("https://images.unsplash.com/photo-1560518883-ce09059eeffa?w=200")
                .build()
        );

        TENANT_CONFIGS.put("tenant2", new TenantConfigDTO.Builder()
                .id("tenant2")
                .name("Tenant 2 Shop")
                .logoUrl("https://images.unsplash.com/photo-1556912173-3bb406ef7e77?w=200")
                .build()
        );

        TENANT_CONFIGS.put("default", new TenantConfigDTO.Builder()
                .id("default")
                .name("Home Decor Admin")
                .logoUrl(null)
                .build()
        );
    }

    public AuthService(AdminUserRepository adminUserRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.adminUserRepository = adminUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        AdminUser user = adminUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    return new RuntimeException("Invalid credentials");
                });
        String finalTenantId =user.getTenantId();
        System.out.println("User found, checking password...");
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            System.out.println("Password mismatch for user: " + request.getEmail());
            throw new RuntimeException("Invalid credentials");
        }
        System.out.println("Login successful for: " + request.getEmail());

        if (!user.getEnabled()) {
            throw new RuntimeException("Account is disabled");
        }

        // Update last login
        user.setLastLogin(LocalDateTime.now());
        adminUserRepository.save(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(
            user.getEmail(),
            finalTenantId,
            user.getRoles()
        );

        // Convert user to DTO
        AdminUserDTO userDTO = convertToDTO(user, finalTenantId);

        // Get tenant config
        TenantConfigDTO tenantConfig = TENANT_CONFIGS.getOrDefault(finalTenantId, TENANT_CONFIGS.get("default"));

        // Check if 2FA is required
        String sessionId = user.getRequiresTwoFactor() ? UUID.randomUUID().toString() : null;

        return new LoginResponse(
            userDTO,
            token,
            user.getRequiresTwoFactor(),
            sessionId,
            tenantConfig
        );
    }

    public AdminUserDTO getCurrentUser(String email, String tenantId) {
        AdminUser user = adminUserRepository.findByEmailAndTenantId(email, tenantId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user, tenantId);
    }

    private AdminUserDTO convertToDTO(AdminUser user, String tenantId) {
        AdminUserDTO dto = new AdminUserDTO();
        dto.setId(String.valueOf(user.getId()));
        dto.setEmail(user.getEmail());
        dto.setName(user.getName());
        dto.setRoles(user.getRoles());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setLastLogin(user.getLastLogin() != null ? user.getLastLogin().toString() : null);
        dto.setTenantId(tenantId);
        return dto;
    }
}
