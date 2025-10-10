package com.homebase.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homebase.admin.dto.LoginRequest;
import com.homebase.admin.dto.LoginResponse;
import com.homebase.admin.service.AuthService;
import com.homebase.admin.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test cases for AuthController
 * Tests authentication endpoints including login, logout, and password reset
 */
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private JwtUtil jwtUtil;

    private LoginRequest loginRequest;
    private LoginResponse loginResponse;

    @BeforeEach
    void setUp() {
        // Setup test data
        loginRequest = new LoginRequest();
        loginRequest.setEmail("admin@example.com");
        loginRequest.setPassword("password123");
        loginRequest.setTenantId("default");

        loginResponse = new LoginResponse();
        loginResponse.setToken("mock-jwt-token");
        loginResponse.setRefreshToken("mock-refresh-token");
        loginResponse.setRequires2FA(false);
    }

    @Test
    void testLogin_Success() throws Exception {
        // Given
        when(authService.login(any(LoginRequest.class))).thenReturn(loginResponse);

        // When & Then
        mockMvc.perform(post("/api/admin/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("mock-jwt-token"))
                .andExpect(jsonPath("$.refreshToken").value("mock-refresh-token"))
                .andExpect(jsonPath("$.requires2FA").value(false));
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        // Given
        when(authService.login(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Invalid credentials"));

        // When & Then
        mockMvc.perform(post("/api/admin/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    void testLogin_MissingEmail() throws Exception {
        // Given
        loginRequest.setEmail(null);

        // When & Then
        mockMvc.perform(post("/api/admin/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLogin_MissingPassword() throws Exception {
        // Given
        loginRequest.setPassword(null);

        // When & Then
        mockMvc.perform(post("/api/admin/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "admin@example.com")
    void testLogout_Success() throws Exception {
        // When & Then
        mockMvc.perform(post("/api/admin/logout")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Logged out successfully"));
    }

    @Test
    void testPasswordReset_Success() throws Exception {
        // Given
        String requestBody = "{\"email\":\"admin@example.com\"}";

        // When & Then
        mockMvc.perform(post("/api/admin/password-reset")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Password reset email sent"));
    }

    @Test
    void testVerify2FA_Success() throws Exception {
        // Given
        String requestBody = "{\"code\":\"123456\",\"sessionId\":\"session-123\"}";

        // When & Then
        mockMvc.perform(post("/api/admin/verify-2fa")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    void testVerify2FA_InvalidCode() throws Exception {
        // Given
        String requestBody = "{\"code\":\"wrong\",\"sessionId\":\"session-123\"}";

        // When & Then
        mockMvc.perform(post("/api/admin/verify-2fa")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(false));
    }
}
