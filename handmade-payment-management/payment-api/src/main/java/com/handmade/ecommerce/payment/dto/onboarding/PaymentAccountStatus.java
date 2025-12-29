package com.handmade.ecommerce.payment.dto.onboarding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * Payment account status
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentAccountStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountId;
    private String gateway;
    private boolean isVerified;
    private boolean payoutsEnabled;
    private boolean chargesEnabled;
    private String verificationStatus;
    private String details;
}
