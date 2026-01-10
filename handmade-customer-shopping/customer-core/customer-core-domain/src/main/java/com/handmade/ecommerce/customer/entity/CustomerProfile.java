package com.handmade.ecommerce.customer.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_customer_profile
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_customer_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CustomerProfile extends BaseJpaEntity {
    
    @Column(name = "user_id", nullable = false, length = 36, unique = true)
    private String userId;
    @Column(name = "first_name", length = 100)
    private String firstName;
    @Column(name = "last_name", length = 100)
    private String lastName;
    @Column(name = "email", nullable = false, length = 255, unique = true)
    private String email;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;
    @Column(name = "is_phone_verified")
    private Boolean isPhoneVerified;
    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
