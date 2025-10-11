package com.homebase.admin.mapper;

import com.homebase.admin.entity.Cart;
import com.homebase.admin.entity.CartItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface CartMapper {

    Cart findByCustomerIdAndTenantId(@Param("customerId") Long customerId, @Param("tenantId") String tenantId);

    List<CartItem> findCartItemsByCartId(@Param("cartId") Long cartId, @Param("tenantId") String tenantId);

    CartItem findCartItemById(@Param("id") Long id, @Param("tenantId") String tenantId);

    CartItem findCartItemByCartAndProduct(@Param("cartId") Long cartId, @Param("productId") Long productId, @Param("tenantId") String tenantId);

    BigDecimal calculateCartTotal(@Param("cartId") Long cartId, @Param("tenantId") String tenantId);

    int countCartItems(@Param("cartId") Long cartId, @Param("tenantId") String tenantId);
}
