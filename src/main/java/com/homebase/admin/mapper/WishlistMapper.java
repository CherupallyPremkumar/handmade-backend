package com.homebase.admin.mapper;

import com.homebase.admin.entity.Wishlist;
import com.homebase.admin.entity.WishlistItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WishlistMapper {

    Wishlist findByCustomerIdAndTenantId(@Param("customerId") Long customerId, @Param("tenantId") String tenantId);

    List<WishlistItem> findWishlistItemsByWishlistId(@Param("wishlistId") Long wishlistId, @Param("tenantId") String tenantId);

    WishlistItem findWishlistItemByWishlistAndProduct(@Param("wishlistId") Long wishlistId, @Param("productId") Long productId, @Param("tenantId") String tenantId);

    int countWishlistItems(@Param("wishlistId") Long wishlistId, @Param("tenantId") String tenantId);

    boolean existsInWishlist(@Param("wishlistId") Long wishlistId, @Param("productId") Long productId, @Param("tenantId") String tenantId);
}
