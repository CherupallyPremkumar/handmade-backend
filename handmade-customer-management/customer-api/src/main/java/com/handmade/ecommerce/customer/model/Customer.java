package com.handmade.ecommerce.customer.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Customer - Aggregate Root for Customer Management
 * Linked to UserAccount for authentication
 */
@Entity
@Table(name = "customers", indexes = {
    @Index(name = "idx_customer_user", columnList = "user_id"),
    @Index(name = "idx_customer_email", columnList = "email")
})
public class Customer {
    
    @Id
    @Column(name = "customer_id", length = 50)
    private String customerId;
    
    @Column(name = "user_id", length = 50, unique = true)
    private String userId; // ← Links to UserAccount.userId
    
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;
    
    @Column(name = "first_name", length = 100)
    private String firstName;
    
    @Column(name = "last_name", length = 100)
    private String lastName;
    
    @Column(name = "phone", length = 20)
    private String phone;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "customer_status", length = 20)
    private CustomerStatus customerStatus;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
    
    @Column(name = "default_shipping_address_id", length = 50)
    private String defaultShippingAddressId;
    
    @Column(name = "default_billing_address_id", length = 50)
    private String defaultBillingAddressId;
    
    @Column(name = "created_at")
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    @Column(name = "last_login_at")
    private Instant lastLoginAt;
    
    // Constructors
    public Customer() {
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
        this.customerStatus = CustomerStatus.ACTIVE;
    }
    
    public Customer(String customerId, String email, String firstName, String lastName) {
        this();
        this.customerId = customerId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    // Business methods
    public void addAddress(Address address) {
        addresses.add(address);
        address.setCustomer(this);
    }
    
    public void removeAddress(Address address) {
        addresses.remove(address);
        address.setCustomer(null);
    }
    
    public void setDefaultShippingAddress(Address address) {
        if (addresses.contains(address)) {
            this.defaultShippingAddressId = address.getAddressId();
        }
    }
    
    public void setDefaultBillingAddress(Address address) {
        if (addresses.contains(address)) {
            this.defaultBillingAddressId = address.getAddressId();
        }
    }
    
    public void suspend() {
        this.customerStatus = CustomerStatus.SUSPENDED;
        this.updatedAt = Instant.now();
    }
    
    public void activate() {
        this.customerStatus = CustomerStatus.ACTIVE;
        this.updatedAt = Instant.now();
    }
    
    public void recordLogin() {
        this.lastLoginAt = Instant.now();
    }
    
    // Getters and Setters
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { 
        this.email = email;
        this.updatedAt = Instant.now();
    }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { 
        this.firstName = firstName;
        this.updatedAt = Instant.now();
    }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { 
        this.lastName = lastName;
        this.updatedAt = Instant.now();
    }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { 
        this.phone = phone;
        this.updatedAt = Instant.now();
    }
    
    public CustomerStatus getCustomerStatus() { return customerStatus; }
    public void setCustomerStatus(CustomerStatus customerStatus) { 
        this.customerStatus = customerStatus;
        this.updatedAt = Instant.now();
    }
    
    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }
    
    public String getDefaultShippingAddressId() { return defaultShippingAddressId; }
    public void setDefaultShippingAddressId(String defaultShippingAddressId) { 
        this.defaultShippingAddressId = defaultShippingAddressId; 
    }
    
    public String getDefaultBillingAddressId() { return defaultBillingAddressId; }
    public void setDefaultBillingAddressId(String defaultBillingAddressId) { 
        this.defaultBillingAddressId = defaultBillingAddressId; 
    }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    public Instant getLastLoginAt() { return lastLoginAt; }
    public void setLastLoginAt(Instant lastLoginAt) { this.lastLoginAt = lastLoginAt; }
    
    /**
     * Customer Status Enum
     */
    public enum CustomerStatus {
        ACTIVE,
        SUSPENDED,
        DELETED
    }
}
