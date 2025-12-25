package com.handmade.ecommerce.platform.api.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Request DTO for revising commission structure.
 */
public class ReviseCommissionRequest {
    
    @NotBlank(message = "Operator ID is required")
    private String operatorId;
    
    @NotNull(message = "Take rate is required")
    @DecimalMin(value = "0.0", message = "Take rate must be non-negative")
    private BigDecimal newTakeRate;
    
    @NotNull(message = "Flat fee is required")
    @DecimalMin(value = "0.0", message = "Flat fee must be non-negative")
    private BigDecimal flatFee;
    
    @NotBlank(message = "Currency is required")
    private String currency;
    
    private String reason;

    public ReviseCommissionRequest() {}

    public ReviseCommissionRequest(String operatorId, BigDecimal newTakeRate, BigDecimal flatFee, String currency, String reason) {
        this.operatorId = operatorId;
        this.newTakeRate = newTakeRate;
        this.flatFee = flatFee;
        this.currency = currency;
        this.reason = reason;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public BigDecimal getNewTakeRate() {
        return newTakeRate;
    }

    public void setNewTakeRate(BigDecimal newTakeRate) {
        this.newTakeRate = newTakeRate;
    }

    public BigDecimal getFlatFee() {
        return flatFee;
    }

    public void setFlatFee(BigDecimal flatFee) {
        this.flatFee = flatFee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
