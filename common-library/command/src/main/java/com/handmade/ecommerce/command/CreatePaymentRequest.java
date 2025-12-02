package com.handmade.ecommerce.command;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {

    @NotBlank(message = "Checkout ID is required")
    private String checkoutId;

    @NotBlank(message = "Buyer ID is required")
    private String buyerId;

    private BigDecimal totalAmount;
    private String currency;

    @NotEmpty(message = "Payment orders cannot be empty")
    @Valid
    private List<PaymentOrderRequest> paymentOrders;

    /**
     * Redirect URL after payment completion
     * Used for hosted payment page flow
     */
    private String redirectUrl;
}
