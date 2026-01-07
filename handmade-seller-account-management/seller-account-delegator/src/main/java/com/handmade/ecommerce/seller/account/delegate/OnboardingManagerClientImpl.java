package com.handmade.ecommerce.seller.account.delegate;

import com.handmade.ecommerce.seller.account.api.SellerAccountService;
import com.handmade.ecommerce.seller.account.dto.ActiveSellerAccountView;
import com.handmade.ecommerce.seller.account.dto.CreateSellerAccountRequest;
import com.handmade.ecommerce.seller.account.dto.LockPolicyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class OnboardingManagerClientImpl implements OnboardingManagerClient {

    @Autowired
    private SellerAccountService sellerAccountService;

    @Override
    public boolean existsByEmail(String email) {
        return sellerAccountService.existsByEmail(email);
    }

    @Override
    public String createCase(CreateSellerAccountRequest request) {
        return sellerAccountService.createCase(request);
    }

    @Override
    public Optional<ActiveSellerAccountView> findActiveCase(String email, String country) {
        return sellerAccountService.findActiveCase(email, country);
    }

    @Override
    public void lockPolicy(String caseId, LockPolicyRequest lockPolicyRequest) {
        sellerAccountService.lockPolicy(caseId, lockPolicyRequest);
    }
}
