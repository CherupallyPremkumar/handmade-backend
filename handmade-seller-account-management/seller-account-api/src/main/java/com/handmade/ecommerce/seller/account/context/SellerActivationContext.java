package com.handmade.ecommerce.seller.account.context;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Context for the seller activation orchestration flow.
 */
@Getter
@Setter
@ToString
public class SellerActivationContext implements Serializable {
    private static final long serialVersionUID = 1L;

    private String workflowId;
    private String onboardingCaseId;
    private String sellerId;
    private String email;
    private String country;

    // Progress tracking
    private boolean sellerCreated;
    private boolean storeCreated;
    private boolean payoutProfileInitialized;
    private boolean complianceSnapshotCreated;

    // Result data
    private String storeId;
}
