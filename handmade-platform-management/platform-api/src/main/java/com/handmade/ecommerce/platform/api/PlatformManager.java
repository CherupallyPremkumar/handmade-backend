package com.handmade.ecommerce.platform.api;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import org.chenile.workflow.api.StateEntityService;

/**
 * Platform Service Interface
 * Similar to ProcessManager in chenile-process-management
 * This is the main service interface for platform management
 */
public interface PlatformManager extends StateEntityService<PlatformOwner> {
    // All CRUD and state management methods are inherited from StateEntityService
    // Additional platform-specific methods can be added here if needed
}
