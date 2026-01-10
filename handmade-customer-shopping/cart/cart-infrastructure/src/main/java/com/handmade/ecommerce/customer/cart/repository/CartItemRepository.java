package com.handmade.ecommerce.customer.cart;

import com.handmade.ecommerce.customer.cart.entity.CartItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for CartItem
 * Generated from entity file
 */
@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {
    
    List<CartItem> findByCartId(String cartId);
    List<CartItem> findByOfferId(String offerId);
    List<CartItem> findByProductId(String productId);
    List<CartItem> findByProductName(String productName);
    List<CartItem> findBySellerId(String sellerId);
    List<CartItem> findBySellerName(String sellerName);
    List<CartItem> findByCurrencyCode(String currencyCode);
    List<CartItem> findByAppliedPromotionId(String appliedPromotionId);
    List<CartItem> findByAppliedCouponCode(String appliedCouponCode);
    List<CartItem> findByGiftWrapType(String giftWrapType);
    List<CartItem> findByAvailabilityStatus(String availabilityStatus);
}
