package com.handmade.ecommerce.platform.event;

import java.io.Serializable;
import java.util.Date;

/**
 * Event emitted when the platform enters emergency lockdown.
 */
public class PlatformLockdownInitiated implements Serializable {
    private String platformId;
    private String reason;
    private String operatorId;
    private Date occurredAt;

    public PlatformLockdownInitiated() {}

    public PlatformLockdownInitiated(String platformId, String reason, String operatorId) {
        this.platformId = platformId;
        this.reason = reason;
        this.operatorId = operatorId;
        this.occurredAt = new Date();
    }

    public String getPlatformId() { return platformId; }
    public void setPlatformId(String platformId) { this.platformId = platformId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getOperatorId() { return operatorId; }
    public void setOperatorId(String operatorId) { this.operatorId = operatorId; }

    public Date getOccurredAt() { return occurredAt; }
    public void setOccurredAt(Date occurredAt) { this.occurredAt = occurredAt; }
}
