package com.handmade.ecommerce.platform.domain.event;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import java.time.LocalDateTime;

public class PlatformDeletedEvent {
    
    public String platformId;
    public String platformName;
    public LocalDateTime deletedAt;
    public String deletedBy;
    
    public PlatformDeletedEvent(PlatformOwner platform) {
        this.platformId = platform.id;
        this.platformName = platform.name;
        this.deletedAt = platform.deletedAt;
        this.deletedBy = platform.deletedBy;
    }
}
