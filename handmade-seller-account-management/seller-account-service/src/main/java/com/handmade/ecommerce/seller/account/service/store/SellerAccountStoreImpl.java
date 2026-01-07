package com.handmade.ecommerce.seller.account.service.store;

import com.handmade.ecommerce.seller.account.infrastructure.persistence.SellerAccountRepository;
import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerAccountStoreImpl implements SellerAccountStore<SellerAccount> {
    @Autowired
    private SellerAccountRepository repository;

    @Override
    public void store(SellerAccount entity) {
        repository.save(entity);
    }

    @Override
    public SellerAccount retrieve(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
