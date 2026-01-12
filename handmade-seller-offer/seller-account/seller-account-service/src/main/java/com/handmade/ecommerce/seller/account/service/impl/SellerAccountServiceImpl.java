package com.handmade.ecommerce.seller.account.service.impl;

import com.handmade.ecommerce.seller.SellerAccountRepository;
import com.handmade.ecommerce.seller.account.api.SellerAccountService;
import com.handmade.ecommerce.seller.entity.SellerAccount;

import com.handmade.ecommerce.seller.onboarding.event.OnboardingApprovedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SellerAccountServiceImpl implements SellerAccountService {

    private final Logger logger = LoggerFactory.getLogger(SellerAccountServiceImpl.class);

    @Autowired
    private SellerAccountRepository sellerAccountRepository;

    @Override
    @Transactional
    public void createSellerAccountIfNotExists(OnboardingApprovedEvent event) {
        // 1. Idempotency Check
        Optional<SellerAccount> existingAccount = sellerAccountRepository.findByEmail(event.getEmail());
        if (existingAccount.isPresent()) {
            logger.warn("Seller account already exists for email: {}. Skipping provisioning.", event.getEmail());
            return;
        }

        // 2. Map Event to Entity
        SellerAccount account = SellerAccount.builder()
                .platformId(event.getCaseId())
                .email(event.getEmail())
                .businessName(event.getBusinessName())
                .verified(true)
                .kycCompleted(true)
                .suspended(false)
                .deleted(false)
                .canCreateListings(true)
                .build();

        // 3. Save Entity
        sellerAccountRepository.save(account);
        logger.info("Successfully provisioned SellerAccount for email: {}", event.getEmail());
    }
}
