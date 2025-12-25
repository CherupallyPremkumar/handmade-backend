package com.handmade.ecommerce.platform.domain.event;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import java.time.LocalDateTime;

public class PlatformActivatedEvent {
    
    public String platformId;
    public String platformName;
    public LocalDateTime activatedAt;
    public String activatedBy;
    
    public PlatformActivatedEvent(PlatformOwner platform) {
        this.platformId = platform.id;
        this.platformName = platform.name;
        this.activatedAt = LocalDateTime.now();
    }
}
