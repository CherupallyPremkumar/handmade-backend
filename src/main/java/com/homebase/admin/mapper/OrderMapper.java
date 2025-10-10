package com.homebase.admin.mapper;

import com.homebase.admin.dto.OrderDTO;
import com.homebase.admin.entity.Order;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * MyBatis Mapper for Order queries
 * Provides complex queries that are difficult with JPA
 */
@Mapper
public interface OrderMapper {

    /**
     * Get orders with custom filtering and pagination
     */
    @Select("<script>" +
            "SELECT o.* FROM orders o " +
            "WHERE o.tenant_id = #{tenantId} " +
            "<if test='status != null'> AND o.status = #{status} </if>" +
            "<if test='paymentStatus != null'> AND o.payment_status = #{paymentStatus} </if>" +
            "<if test='customerId != null'> AND o.customer_id = #{customerId} </if>" +
            "<if test='startDate != null'> AND o.created_at >= #{startDate} </if>" +
            "<if test='endDate != null'> AND o.created_at <= #{endDate} </if>" +
            "ORDER BY o.created_at DESC " +
            "<if test='limit != null'> LIMIT #{limit} </if>" +
            "<if test='offset != null'> OFFSET #{offset} </if>" +
            "</script>")
    List<Order> findOrdersWithFilters(
            @Param("tenantId") String tenantId,
            @Param("status") String status,
            @Param("paymentStatus") String paymentStatus,
            @Param("customerId") Long customerId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate,
            @Param("limit") Integer limit,
            @Param("offset") Integer offset
    );

    /**
     * Get order statistics by date range
     */
    @Select("SELECT " +
            "COUNT(*) as totalOrders, " +
            "SUM(total) as totalRevenue, " +
            "AVG(total) as averageOrderValue, " +
            "COUNT(DISTINCT customer_id) as uniqueCustomers " +
            "FROM orders " +
            "WHERE tenant_id = #{tenantId} " +
            "AND created_at BETWEEN #{startDate} AND #{endDate}")
    Map<String, Object> getOrderStatistics(
            @Param("tenantId") String tenantId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    /**
     * Get top selling products
     */
    @Select("SELECT " +
            "oi.product_id, " +
            "p.name as product_name, " +
            "SUM(oi.quantity) as total_quantity, " +
            "SUM(oi.price * oi.quantity) as total_revenue " +
            "FROM order_items oi " +
            "JOIN orders o ON oi.order_id = o.id " +
            "JOIN products p ON oi.product_id = p.id " +
            "WHERE o.tenant_id = #{tenantId} " +
            "AND o.status NOT IN ('CANCELLED', 'PAYMENT_FAILED') " +
            "GROUP BY oi.product_id, p.name " +
            "ORDER BY total_quantity DESC " +
            "LIMIT #{limit}")
    List<Map<String, Object>> getTopSellingProducts(
            @Param("tenantId") String tenantId,
            @Param("limit") int limit
    );

    /**
     * Get revenue by date (daily, weekly, monthly)
     */
    @Select("<script>" +
            "SELECT " +
            "DATE_TRUNC(#{period}, created_at) as period_date, " +
            "COUNT(*) as order_count, " +
            "SUM(total) as revenue " +
            "FROM orders " +
            "WHERE tenant_id = #{tenantId} " +
            "AND status = 'CONFIRMED' " +
            "AND payment_status = 'PAID' " +
            "<if test='startDate != null'> AND created_at >= #{startDate} </if>" +
            "<if test='endDate != null'> AND created_at <= #{endDate} </if>" +
            "GROUP BY period_date " +
            "ORDER BY period_date DESC" +
            "</script>")
    List<Map<String, Object>> getRevenueByPeriod(
            @Param("tenantId") String tenantId,
            @Param("period") String period,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );

    /**
     * Get customer order history with totals
     */
    @Select("SELECT " +
            "c.id as customer_id, " +
            "c.name as customer_name, " +
            "c.email as customer_email, " +
            "COUNT(o.id) as total_orders, " +
            "SUM(o.total) as total_spent, " +
            "MAX(o.created_at) as last_order_date " +
            "FROM customers c " +
            "LEFT JOIN orders o ON c.id = o.customer_id " +
            "WHERE c.tenant_id = #{tenantId} " +
            "GROUP BY c.id, c.name, c.email " +
            "ORDER BY total_spent DESC " +
            "LIMIT #{limit}")
    List<Map<String, Object>> getTopCustomers(
            @Param("tenantId") String tenantId,
            @Param("limit") int limit
    );

    /**
     * Search orders by customer name, email, or order number
     */
    @Select("<script>" +
            "SELECT o.* FROM orders o " +
            "WHERE o.tenant_id = #{tenantId} " +
            "AND (" +
            "o.order_number LIKE CONCAT('%', #{searchTerm}, '%') " +
            "OR o.customer_name LIKE CONCAT('%', #{searchTerm}, '%') " +
            "OR o.customer_email LIKE CONCAT('%', #{searchTerm}, '%') " +
            "OR o.customer_phone LIKE CONCAT('%', #{searchTerm}, '%')" +
            ") " +
            "ORDER BY o.created_at DESC " +
            "LIMIT #{limit}" +
            "</script>")
    List<Order> searchOrders(
            @Param("tenantId") String tenantId,
            @Param("searchTerm") String searchTerm,
            @Param("limit") int limit
    );

    /**
     * Get pending orders count by status
     */
    @Select("SELECT status, COUNT(*) as count " +
            "FROM orders " +
            "WHERE tenant_id = #{tenantId} " +
            "AND status IN ('PENDING', 'CONFIRMED', 'PROCESSING') " +
            "GROUP BY status")
    List<Map<String, Object>> getPendingOrdersCount(@Param("tenantId") String tenantId);

    /**
     * Get abandoned carts (orders in PENDING with old created_at)
     */
    @Select("SELECT o.* FROM orders o " +
            "WHERE o.tenant_id = #{tenantId} " +
            "AND o.status = 'PENDING' " +
            "AND o.payment_status = 'PENDING' " +
            "AND o.created_at < NOW() - INTERVAL '#{hours} hours' " +
            "ORDER BY o.created_at DESC")
    List<Order> getAbandonedOrders(
            @Param("tenantId") String tenantId,
            @Param("hours") int hours
    );
}
