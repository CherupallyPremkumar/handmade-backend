package com.handmade.ecommerce.seller;

import com.handmade.ecommerce.seller.domain.aggregate.SellerAccount;
import com.handmade.ecommerce.seller.dto.CreateSellerRequest;
import org.chenile.workflow.api.StateEntityService;

/**
 * Manager for SellerAccount aggregate (Onboarding)
 */
public interface SellerAccountManager extends StateEntityService<SellerAccount> {
    SellerAccount registerSellerAccount(CreateSellerRequest request);
}
