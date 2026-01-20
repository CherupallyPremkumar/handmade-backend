package com.handmade.ecommerce.domain.customer;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * CustomerProfile - Customer account profile
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_customer_profile")
public class CustomerProfile extends BaseJpaEntity {

    @Column(name = "user_id", length = 36, nullable = false, unique = true)
    private String userId; // Link to Auth System (Keycloak/Cognito)

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "email", length = 255, nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified = false;

    @Column(name = "is_phone_verified")
    private Boolean isPhoneVerified = false;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;
}
