package com.handmade.ecommerce.offer.dto;

import lombok.Data;

/**
 * Payload for Offer state transitions.
 */
@Data
public class OfferTransitionPayload {
    private String reason;
    private String actorId;
    private String comment;
}
