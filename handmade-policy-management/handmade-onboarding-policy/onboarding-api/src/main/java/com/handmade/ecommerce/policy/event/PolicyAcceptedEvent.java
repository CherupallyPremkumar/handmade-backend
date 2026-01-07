package com.handmade.ecommerce.policy.event;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Event emitted when a seller accepts or locks a platform policy.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PolicyAcceptedEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String sellerId;
    private String policyId;
    private String onboardingCaseId;
    private LocalDateTime acceptedAt;
}
