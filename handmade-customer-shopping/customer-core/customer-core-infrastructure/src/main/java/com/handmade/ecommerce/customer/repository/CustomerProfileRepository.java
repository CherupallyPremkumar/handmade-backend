package com.handmade.ecommerce.customer;

import com.handmade.ecommerce.customer.entity.CustomerProfile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CustomerProfile
 * Generated from entity file
 */
@Repository
public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, String> {
    
    Optional<CustomerProfile> findByUserId(String userId);
    List<CustomerProfile> findByFirstName(String firstName);
    List<CustomerProfile> findByLastName(String lastName);
    Optional<CustomerProfile> findByEmail(String email);
    List<CustomerProfile> findByPhoneNumber(String phoneNumber);
    List<CustomerProfile> findByIsEmailVerified(Boolean isEmailVerified);
    List<CustomerProfile> findByIsPhoneVerified(Boolean isPhoneVerified);

    // Existence checks
    boolean existsByUserId(String userId);
    boolean existsByEmail(String email);
}
