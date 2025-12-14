//package com.handmade.ecommerce.sellerorch.service;
//
//import com.handmade.ecommerce.seller.model.Seller;
//import com.handmade.ecommerce.seller.model.SellerPaymentInfo;
//import org.springframework.stereotype.Service;
//
//@Service
//public class SetupBankAccountCommandService extends BaseSellerOrchService {
//
//    @Override
//    protected void doProcess(SellerContext sellerContext) {
//        Seller seller = getSeller(sellerContext);
//
//        if (seller == null) {
//            throw new IllegalStateException("Seller not found in context");
//        }
//
//        // Validate bank account information
//        validateBankAccountInfo(seller);
//
//        // Setup payment info if not already exists
////        if (seller.getPaymentInfo() == null) {
////            SellerPaymentInfo paymentInfo = new SellerPaymentInfo();
////            seller.setPaymentInfo(paymentInfo);
////        }
//
//        // TODO: Verify bank account with external service
//        // bankVerificationService.verifyAccount(seller.getPaymentInfo());
//
//        // TODO: Store bank account details securely
//        // paymentService.storeBankDetails(seller);
//
//        System.out.println("Bank account setup completed for seller: " + seller.getSellerName());
//        super.doProcess(sellerContext);
//    }
//
//    private void validateBankAccountInfo(Seller seller) {
//        SellerPaymentInfo paymentInfo = seller.getPaymentInfo();
//
//        if (paymentInfo == null) {
//            throw new IllegalArgumentException("Payment information is required");
//        }
//
//        // Validate bank account number
//        if (paymentInfo.getBankAccountNumber() == null || paymentInfo.getBankAccountNumber().trim().isEmpty()) {
//            throw new IllegalArgumentException("Bank account number is required");
//        }
//
//        // Validate routing number if applicable
//        if (paymentInfo.getRoutingNumber() == null || paymentInfo.getRoutingNumber().trim().isEmpty()) {
//            throw new IllegalArgumentException("Routing number is required");
//        }
//
//        // Validate account holder name
//        if (paymentInfo.getAccountHolderName() == null || paymentInfo.getAccountHolderName().trim().isEmpty()) {
//            throw new IllegalArgumentException("Account holder name is required");
//        }
//
//        // Ensure account holder name matches seller name
//        if (!paymentInfo.getAccountHolderName().equalsIgnoreCase(seller.getSellerName())) {
//            System.out.println("Warning: Account holder name does not match seller name");
//        }
//    }
//
//    private Seller getSeller(SellerContext context) {
//        // Get seller from context data
//        return (Seller) context.get("seller");
//    }
//}
