package com.homebase.admin.mapper;

import com.homebase.admin.entity.Order;
import com.homebase.admin.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface OrderMapper {

    List<Order> findByCustomerIdAndTenantId(@Param("customerId") Long customerId, @Param("tenantId") String tenantId);

    Order findByIdAndTenantId(@Param("id") Long id, @Param("tenantId") String tenantId);

    Order findByOrderNumberAndTenantId(@Param("orderNumber") String orderNumber, @Param("tenantId") String tenantId);

    List<Order> findByStatusAndTenantId(@Param("status") String status, @Param("tenantId") String tenantId);

    List<Order> findByPaymentStatusAndTenantId(@Param("paymentStatus") String paymentStatus, @Param("tenantId") String tenantId);

    List<Order> findAllByTenantIdPaginated(@Param("tenantId") String tenantId, @Param("limit") int limit, @Param("offset") int offset);

    List<OrderItem> findOrderItemsByOrderId(@Param("orderId") Long orderId, @Param("tenantId") String tenantId);

    long countByTenantId(@Param("tenantId") String tenantId);

    long countByStatusAndTenantId(@Param("status") String status, @Param("tenantId") String tenantId);

    BigDecimal getTotalRevenue(@Param("tenantId") String tenantId);

    BigDecimal getRevenueByDateRange(@Param("tenantId") String tenantId, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
