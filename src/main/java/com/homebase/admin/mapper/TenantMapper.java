package com.homebase.admin.mapper;

import com.homebase.admin.entity.Tenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TenantMapper {

    Tenant findByUrlPath(@Param("urlPath") String urlPath);

    Tenant findById(@Param("id") Long id);

    Tenant findByTenantCode(@Param("tenantCode") String tenantCode);

    List<Tenant> findAllTenants();

    boolean existsByUrlPath(@Param("urlPath") String urlPath);

    boolean existsByTenantCode(@Param("tenantCode") String tenantCode);

    long countTenants();
}
