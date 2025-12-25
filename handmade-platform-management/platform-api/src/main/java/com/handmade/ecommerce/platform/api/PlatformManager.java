package com.handmade.ecommerce.platform.api;


import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import org.chenile.workflow.api.StateEntityService;


public interface PlatformManager extends StateEntityService<PlatformOwner> {

}
