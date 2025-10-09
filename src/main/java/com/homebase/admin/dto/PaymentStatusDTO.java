package com.homebase.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusDTO {
    private String status; // PENDING, SUCCESS, FAILED
    private String orderId;
    private String transactionId;
    private String message;
}
