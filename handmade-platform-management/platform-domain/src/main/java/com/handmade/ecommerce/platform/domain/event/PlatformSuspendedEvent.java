package com.handmade.ecommerce.platform.domain.event;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import java.time.LocalDateTime;

public class PlatformSuspendedEvent {
    
    public String platformId;
    public String platformName;
    public String suspensionReason;
    public LocalDateTime suspendedAt;
    public String suspendedBy;
    
    public PlatformSuspendedEvent(PlatformOwner platform) {
        this.platformId = platform.id;
        this.platformName = platform.name;
        this.suspensionReason = platform.suspensionReason;
        this.suspendedAt = LocalDateTime.now();
    }
}
