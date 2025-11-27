package com.handmade.ecommerce.sellerorch;

/**
 * Service interface for seller orchestration operations.
 */
public interface SellerorchService {
    /**
     * Creates a new seller through the orchestration workflow.
     *
     * @param sellerContext the context containing seller creation details
     */
    void create(SellerContext sellerContext);
}
