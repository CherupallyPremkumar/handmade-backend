package com.handmade.ecommerce.platform.delegate;

import com.handmade.ecommerce.platform.model.PlatformOwner;
import com.handmade.ecommerce.platform.dto.*;

/**
 * Business Delegate for Platform Management
 * Allows remote calls to the Platform Manager service
 * Following the same delegation pattern as Chenile Process Management
 */
public interface PlatformManagerClient {
    
    // Platform Lifecycle Operations
    PlatformOwner createPlatform(PlatformOwner platform);
    PlatformOwner activatePlatform(String id, ActivatePlatformPayload payload);
    PlatformOwner suspendPlatform(String id, SuspendPlatformPayload payload);
    PlatformOwner reactivatePlatform(String id, ReactivatePlatformPayload payload);
    PlatformOwner deletePlatform(String id, DeletePlatformPayload payload);
    
    // Generic Event Processing
    PlatformOwner process(String id, String event, Object payload);
    
    // Query Operations
    PlatformOwner getPlatform(String id);
}
