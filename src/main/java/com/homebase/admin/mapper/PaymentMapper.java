package com.homebase.admin.mapper;

import com.homebase.admin.entity.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface PaymentMapper {

    List<Payment> findByOrderIdAndTenantId(@Param("orderId") String orderId, @Param("tenantId") String tenantId);

    Payment findByIdAndTenantId(@Param("id") String id, @Param("tenantId") String tenantId);

    Payment findByTransactionIdAndTenantId(@Param("transactionId") String transactionId, @Param("tenantId") String tenantId);

    List<Payment> findByStatusAndTenantId(@Param("status") String status, @Param("tenantId") String tenantId);

    BigDecimal getTotalSuccessfulPayments(@Param("tenantId") String tenantId);

    long countFailedPayments(@Param("tenantId") String tenantId);
}
