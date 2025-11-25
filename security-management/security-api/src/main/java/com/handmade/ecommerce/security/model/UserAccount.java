package com.handmade.ecommerce.security.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * UserAccount - Authentication and Authorization entity
 * Handles login credentials, roles, and permissions
 */
@Entity
@Table(name = "user_accounts", indexes = {
    @Index(name = "idx_user_email", columnList = "email", unique = true),
    @Index(name = "idx_user_username", columnList = "username", unique = true)
})
public class UserAccount {
    
    @Id
    @Column(name = "user_id", length = 50)
    private String userId;
    
    @Column(name = "username", length = 100, unique = true, nullable = false)
    private String username;
    
    @Column(name = "email", length = 255, unique = true, nullable = false)
    private String email;
    
    @Column(name = "password_hash", length = 255, nullable = false)
    private String passwordHash;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", length = 20)
    private AccountStatus accountStatus;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles = new HashSet<>();
    
    @Column(name = "email_verified")
    private Boolean emailVerified;
    
    @Column(name = "phone_verified")
    private Boolean phoneVerified;
    
    @Column(name = "mfa_enabled")
    private Boolean mfaEnabled;
    
    @Column(name = "mfa_secret", length = 100)
    private String mfaSecret;
    
    @Column(name = "failed_login_attempts")
    private Integer failedLoginAttempts;
    
    @Column(name = "locked_until")
    private Instant lockedUntil;
    
    @Column(name = "last_login_at")
    private Instant lastLoginAt;
    
    @Column(name = "last_login_ip", length = 45)
    private String lastLoginIp;
    
    @Column(name = "password_changed_at")
    private Instant passwordChangedAt;
    
    @Column(name = "created_at")
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    // Constructors
    public UserAccount() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.accountStatus = AccountStatus.ACTIVE;
        this.emailVerified = false;
        this.phoneVerified = false;
        this.mfaEnabled = false;
        this.failedLoginAttempts = 0;
    }
    
    public UserAccount(String userId, String email, String username, String passwordHash) {
        this();
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
    }
    
    // Business methods
    public void addRole(UserRole role) {
        this.roles.add(role);
        this.updatedAt = Instant.now();
    }
    
    public void removeRole(UserRole role) {
        this.roles.remove(role);
        this.updatedAt = Instant.now();
    }
    
    public boolean hasRole(UserRole role) {
        return this.roles.contains(role);
    }
    
    public void verifyEmail() {
        this.emailVerified = true;
        this.updatedAt = Instant.now();
    }
    
    public void enableMfa(String secret) {
        this.mfaEnabled = true;
        this.mfaSecret = secret;
        this.updatedAt = Instant.now();
    }
    
    public void disableMfa() {
        this.mfaEnabled = false;
        this.mfaSecret = null;
        this.updatedAt = Instant.now();
    }
    
    public void recordSuccessfulLogin(String ipAddress) {
        this.lastLoginAt = Instant.now();
        this.lastLoginIp = ipAddress;
        this.failedLoginAttempts = 0;
        this.lockedUntil = null;
        this.updatedAt = Instant.now();
    }
    
    public void recordFailedLogin() {
        this.failedLoginAttempts++;
        this.updatedAt = Instant.now();
        
        // Lock account after 5 failed attempts
        if (this.failedLoginAttempts >= 5) {
            lockAccount(30); // Lock for 30 minutes
        }
    }
    
    public void lockAccount(int minutes) {
        this.accountStatus = AccountStatus.LOCKED;
        this.lockedUntil = Instant.now().plusSeconds(minutes * 60L);
        this.updatedAt = Instant.now();
    }
    
    public void unlockAccount() {
        this.accountStatus = AccountStatus.ACTIVE;
        this.lockedUntil = null;
        this.failedLoginAttempts = 0;
        this.updatedAt = Instant.now();
    }
    
    public void suspendAccount() {
        this.accountStatus = AccountStatus.SUSPENDED;
        this.updatedAt = Instant.now();
    }
    
    public void activateAccount() {
        this.accountStatus = AccountStatus.ACTIVE;
        this.updatedAt = Instant.now();
    }
    
    public boolean isLocked() {
        if (AccountStatus.LOCKED.equals(this.accountStatus)) {
            if (lockedUntil != null && Instant.now().isAfter(lockedUntil)) {
                unlockAccount();
                return false;
            }
            return true;
        }
        return false;
    }
    
    public boolean isActive() {
        return AccountStatus.ACTIVE.equals(this.accountStatus) && !isLocked();
    }
    
    public void changePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
        this.passwordChangedAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    // Getters and Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public AccountStatus getAccountStatus() { return accountStatus; }
    public void setAccountStatus(AccountStatus accountStatus) { this.accountStatus = accountStatus; }
    
    public Set<UserRole> getRoles() { return roles; }
    public void setRoles(Set<UserRole> roles) { this.roles = roles; }
    
    public Boolean getEmailVerified() { return emailVerified; }
    public void setEmailVerified(Boolean emailVerified) { this.emailVerified = emailVerified; }
    
    public Boolean getPhoneVerified() { return phoneVerified; }
    public void setPhoneVerified(Boolean phoneVerified) { this.phoneVerified = phoneVerified; }
    
    public Boolean getMfaEnabled() { return mfaEnabled; }
    public void setMfaEnabled(Boolean mfaEnabled) { this.mfaEnabled = mfaEnabled; }
    
    public String getMfaSecret() { return mfaSecret; }
    public void setMfaSecret(String mfaSecret) { this.mfaSecret = mfaSecret; }
    
    public Integer getFailedLoginAttempts() { return failedLoginAttempts; }
    public void setFailedLoginAttempts(Integer failedLoginAttempts) { this.failedLoginAttempts = failedLoginAttempts; }
    
    public Instant getLockedUntil() { return lockedUntil; }
    public void setLockedUntil(Instant lockedUntil) { this.lockedUntil = lockedUntil; }
    
    public Instant getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(Instant lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    
    public String getLastLoginIp() { return lastLoginIp; }
    public void setLastLoginIp(String lastLoginIp) { this.lastLoginIp = lastLoginIp; }
    
    public Instant getPasswordChangedAt() { return passwordChangedAt; }
    public void setPasswordChangedAt(Instant passwordChangedAt) { this.passwordChangedAt = passwordChangedAt; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    /**
     * User Role Enum
     */
    public enum UserRole {
        CUSTOMER,      // Can buy products
        SELLER,        // Can sell products
        ADMIN,         // Platform administrator
        SUPPORT,       // Customer support
        SUPER_ADMIN    // Full system access
    }
    
    /**
     * Account Status Enum
     */
    public enum AccountStatus {
        ACTIVE,
        LOCKED,
        SUSPENDED,
        DELETED
    }
}
