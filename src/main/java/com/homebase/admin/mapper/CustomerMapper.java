package com.homebase.admin.mapper;

import com.homebase.admin.entity.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerMapper {

    Customer findByEmailAndTenantId(@Param("email") String email, @Param("tenantId") String tenantId);

    Customer findByIdAndTenantId(@Param("id") String id, @Param("tenantId") String tenantId);

    List<Customer> findAllByTenantId(@Param("tenantId") String tenantId);

    List<Customer> searchByNameAndTenantId(@Param("search") String search, @Param("tenantId") String tenantId);

    long countByTenantId(@Param("tenantId") String tenantId);

    boolean existsByEmailAndTenantId(@Param("email") String email, @Param("tenantId") String tenantId);
}
