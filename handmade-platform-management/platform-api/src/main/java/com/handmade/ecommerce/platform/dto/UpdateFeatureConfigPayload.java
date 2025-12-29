package com.handmade.ecommerce.platform.dto;

import lombok.Data;

/**
 * Payload for updating platform feature configuration
 */
@Data
public class UpdateFeatureConfigPayload {
    private Boolean isSellerRegistrationOpen;
    private Boolean isCheckoutEnabled;
    private Boolean isGuestCheckoutAllowed;
    private Boolean isReviewSystemEnabled;
    private Boolean isWishlistEnabled;
    private String configNotes;
}
