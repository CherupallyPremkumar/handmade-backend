package com.homebase.admin.controller;

import com.homebase.admin.dto.AdminProfileDTO;
import com.homebase.admin.dto.UpdateProfileRequest;
import com.homebase.admin.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<AdminProfileDTO> getProfile(Authentication authentication) {
        String email = authentication.getName();
        AdminProfileDTO profile = profileService.getProfile(email);
        return ResponseEntity.ok(profile);
    }

    @PutMapping
    public ResponseEntity<AdminProfileDTO> updateProfile(
            Authentication authentication,
            @RequestBody UpdateProfileRequest request) {
        String email = authentication.getName();
        AdminProfileDTO updated = profileService.updateProfile(email, request);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/avatar")
    public ResponseEntity<Map<String, String>> uploadAvatar(
            Authentication authentication,
            @RequestParam("avatar") MultipartFile file) throws IOException {
        String email = authentication.getName();
        String avatarUrl = profileService.uploadAvatar(email, file);
        
        Map<String, String> response = new HashMap<>();
        response.put("avatarUrl", avatarUrl);
        return ResponseEntity.ok(response);
    }
}
