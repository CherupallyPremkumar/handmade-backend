package com.homebase.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentResponseDTO {
    private Boolean success;
    private String paymentUrl;
    private String transactionId;
    private String merchantTransactionId;
    private String message;
}
