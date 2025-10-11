package com.homebase.admin.mapper;

import com.homebase.admin.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {

    List<Notification> findByCustomerIdAndTenantId(@Param("customerId") String customerId, @Param("tenantId") String tenantId);

    List<Notification> findUnreadByCustomerIdAndTenantId(@Param("customerId") String customerId, @Param("tenantId") String tenantId);

    Notification findByIdAndTenantId(@Param("id") String id, @Param("tenantId") String tenantId);

    long countUnreadByCustomerId(@Param("customerId") String customerId, @Param("tenantId") String tenantId);

    List<Notification> findRecentByCustomerId(@Param("customerId") String customerId, @Param("tenantId") String tenantId, @Param("limit") int limit);
}
