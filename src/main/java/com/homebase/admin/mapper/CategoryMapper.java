package com.homebase.admin.mapper;

import com.homebase.admin.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<Category> findAllByTenantId(@Param("tenantId") String tenantId);

    Category findByIdAndTenantId(@Param("id") Long id, @Param("tenantId") String tenantId);

    Category findBySlugAndTenantId(@Param("slug") String slug, @Param("tenantId") String tenantId);

    long countByTenantId(@Param("tenantId") String tenantId);

    boolean existsBySlugAndTenantId(@Param("slug") String slug, @Param("tenantId") String tenantId);
}
