package com.homebase.admin.service.user;

import com.homebase.admin.dto.user.AuthUserDto;
import com.homebase.admin.dto.user.UserLoginResponse;
import com.homebase.admin.entity.Customer;
import com.homebase.admin.repository.CustomerRepository;
import com.homebase.admin.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

    private final CustomerRepository customerRepository;
    private final JwtUtil jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthUserService(CustomerRepository customerRepository, JwtUtil jwtService, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public UserLoginResponse login(String email, String password) {
        Customer user = customerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getEmail(),user.getTenantId(), user.getRoles());

        return new UserLoginResponse.Builder().user(new AuthUserDto.Builder()
                        .id(String.valueOf(user.getId()))
                        .username(user.getName())
                        .email(user.getEmail())
                        .roles(user.getRoles())
                        .build())
                .token(token)
                .build();
    }
}
