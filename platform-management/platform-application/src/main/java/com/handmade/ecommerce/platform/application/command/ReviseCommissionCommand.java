package com.handmade.ecommerce.platform.application.command;

import java.math.BigDecimal;

/**
 * Command to revise commission structure.
 * Application layer DTO - no business logic.
 */
public class ReviseCommissionCommand {
    private final String operatorId;
    private final BigDecimal newTakeRate;
    private final BigDecimal flatFee;
    private final String currency;
    private final String reason;

    public ReviseCommissionCommand(String operatorId, BigDecimal newTakeRate, 
                                   BigDecimal flatFee, String currency, String reason) {
        this.operatorId = operatorId;
        this.newTakeRate = newTakeRate;
        this.flatFee = flatFee;
        this.currency = currency;
        this.reason = reason;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public BigDecimal getNewTakeRate() {
        return newTakeRate;
    }

    public BigDecimal getFlatFee() {
        return flatFee;
    }

    public String getCurrency() {
        return currency;
    }

    public String getReason() {
        return reason;
    }
}
