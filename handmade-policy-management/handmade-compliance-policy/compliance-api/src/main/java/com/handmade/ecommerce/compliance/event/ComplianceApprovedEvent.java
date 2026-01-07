package com.handmade.ecommerce.compliance.event;

import lombok.Getter;
import lombok.ToString;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@ToString
public class ComplianceApprovedEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String sellerId;
    private final String status;
    private final String comment;
    private final LocalDateTime timestamp;

    public ComplianceApprovedEvent(String sellerId, String status, String comment) {
        this.sellerId = sellerId;
        this.status = status;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }
}
