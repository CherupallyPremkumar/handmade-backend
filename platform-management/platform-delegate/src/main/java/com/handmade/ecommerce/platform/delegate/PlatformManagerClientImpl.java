package com.handmade.ecommerce.platform.delegate;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.dto.ActivatePlatformPayload;
import com.handmade.ecommerce.platform.dto.DeletePlatformPayload;
import com.handmade.ecommerce.platform.dto.ReactivatePlatformPayload;
import com.handmade.ecommerce.platform.dto.SuspendPlatformPayload;
import com.handmade.ecommerce.platform.service.PlatformManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Implementation of Platform Manager Client
 * Delegates calls to remote Platform Service via Chenile Proxy
 */
public class PlatformManagerClientImpl implements PlatformManagerClient {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    @Qualifier("platformServiceProxy")
    private PlatformManager platformServiceProxy;

    @Override
    public PlatformOwner createPlatform(PlatformOwner platform) {
        logger.info("Creating platform via delegate: {}", platform.getId());
        return platformServiceProxy.create(platform).getMutatedEntity();
    }

    @Override
    public PlatformOwner activatePlatform(String id, ActivatePlatformPayload payload) {
        logger.info("Activating platform via delegate: {}", id);
        return process(id, "activate", payload);
    }

    @Override
    public PlatformOwner suspendPlatform(String id, SuspendPlatformPayload payload) {
        logger.info("Suspending platform via delegate: {}", id);
        return process(id, "suspend", payload);
    }

    @Override
    public PlatformOwner reactivatePlatform(String id, ReactivatePlatformPayload payload) {
        logger.info("Reactivating platform via delegate: {}", id);
        return process(id, "reactivate", payload);
    }

    @Override
    public PlatformOwner deletePlatform(String id, DeletePlatformPayload payload) {
        logger.info("Deleting platform via delegate: {}", id);
        return process(id, "delete", payload);
    }

    @Override
    public PlatformOwner process(String id, String event, Object payload) {
        logger.debug("Processing event '{}' for platform: {}", event, id);
        return platformServiceProxy.processById(id, event, payload).getMutatedEntity();
    }

    @Override
    public PlatformOwner getPlatform(String id) {
        logger.debug("Retrieving platform: {}", id);
        return platformServiceProxy.retrieve(id);
    }
}
