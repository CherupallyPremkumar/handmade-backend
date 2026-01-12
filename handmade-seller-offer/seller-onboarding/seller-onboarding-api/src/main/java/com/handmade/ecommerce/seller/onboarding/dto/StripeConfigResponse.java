package com.handmade.ecommerce.seller.onboarding.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StripeConfigResponse implements Serializable {
    private String clientSecret;
    private String publicKey;
    private String flowId;
}
