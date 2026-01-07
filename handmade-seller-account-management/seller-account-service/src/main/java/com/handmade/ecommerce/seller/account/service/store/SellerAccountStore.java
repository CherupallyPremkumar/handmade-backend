package com.handmade.ecommerce.seller.account.service.store;

import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import org.chenile.utils.entity.service.EntityStore;

public interface SellerAccountStore<T> extends EntityStore<SellerAccount> {

    boolean existsByEmail(String email);
}