package com.handmade.ecommerce.sellerorch.service;

import com.handmade.ecommerce.seller.model.Seller;
import com.handmade.ecommerce.seller.dto.command.VerifyKYCPayload;
import com.handmade.ecommerce.seller.service.impl.SellerServiceImpl;
import com.handmade.ecommerce.sellerorch.SellerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyKycCommandService extends BaseSellerOrchService {

    @Autowired
    SellerServiceImpl sellerService;

    @Override
    protected void doProcess(SellerContext sellerContext) {
        Seller seller = getSeller(sellerContext);
        
        if (seller == null) {
            throw new IllegalStateException("Seller not found in context");
        }
        
        // Validate KYC documents are present
        validateKYCDocuments(seller);
        
        // Create KYC verification payload
        VerifyKYCPayload payload = new VerifyKYCPayload();
        payload.setVerified(true); // In real scenario, this would come from external KYC service
        payload.setComments("KYC verification completed successfully");
        
        // Trigger KYC verification event in STM
        try {
            sellerService.processById(seller.getId(), "verifyKYC", payload);
            System.out.println("KYC verification completed for seller: " + seller.getSellerName());
        } catch (Exception e) {
            System.err.println("KYC verification failed: " + e.getMessage());
            throw new RuntimeException("KYC verification failed", e);
        }
        
        super.doProcess(sellerContext);
    }
    
    private void validateKYCDocuments(Seller seller) {
        // Validate that KYC document ID is present
//        if (seller.getKycDocumentId() == null || seller.getKycDocumentId().trim().isEmpty()) {
//            throw new IllegalArgumentException("KYC document ID is required");
//        }
        
        // TODO: Validate document with external KYC service
        // kycService.validateDocument(seller.getKycDocumentId());
        
        // TODO: Check document expiry
        // if (kycService.isDocumentExpired(seller.getKycDocumentId())) {
        //     throw new IllegalArgumentException("KYC document has expired");
        // }
    }
    
    private Seller getSeller(SellerContext context) {
        return (Seller) context.get("seller");
    }
}
