package com.homebase.admin.service;

import com.homebase.admin.dto.AdminProfileDTO;
import com.homebase.admin.dto.UpdateProfileRequest;
import com.homebase.admin.entity.AdminUser;
import com.homebase.admin.entity.TenantContext;
import com.homebase.admin.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final AdminUserRepository adminUserRepository;

    public AdminProfileDTO getProfile(String email) {
        String tenantId = TenantContext.getCurrentTenant();
        AdminUser user = adminUserRepository.findByEmailAndTenantId(email, tenantId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return convertToDTO(user);
    }

    @Transactional
    public AdminProfileDTO updateProfile(String email, UpdateProfileRequest request) {
        String tenantId = TenantContext.getCurrentTenant();
        AdminUser user = adminUserRepository.findByEmailAndTenantId(email, tenantId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());

        AdminUser updated = adminUserRepository.save(user);
        return convertToDTO(updated);
    }

    @Transactional
    public String uploadAvatar(String email, MultipartFile file) throws IOException {
        String tenantId = TenantContext.getCurrentTenant();
        AdminUser user = adminUserRepository.findByEmailAndTenantId(email, tenantId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // For H2/development: store as base64 or external URL
        // In production, upload to S3/cloud storage
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        String avatarUrl = "/uploads/" + fileName;

        // Create uploads directory if it doesn't exist
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save file
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        user.setAvatarUrl(avatarUrl);
        adminUserRepository.save(user);

        return avatarUrl;
    }

    private AdminProfileDTO convertToDTO(AdminUser user) {
        AdminProfileDTO dto = new AdminProfileDTO();
        dto.setId(String.valueOf(user.getId()));
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole().name().toLowerCase());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setCreatedAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null);
        dto.setUpdatedAt(user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null);
        return dto;
    }
}
