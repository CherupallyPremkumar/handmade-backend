package com.homebase.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequestDTO {
    private Long orderId;
    private BigDecimal amount;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
}
