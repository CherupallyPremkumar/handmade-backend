package com.homebase.admin.mapper;

import com.homebase.admin.dto.ProductFilterRequest;
import com.homebase.admin.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> findAllByTenantId(@Param("tenantId") String tenantId);

    Product findByIdAndTenantId(@Param("id") String id, @Param("tenantId") String tenantId);

    List<Product> findByCategoryAndTenantId(@Param("category") String category, @Param("tenantId") String tenantId);

    List<Product> findFeaturedByTenantId(@Param("tenantId") String tenantId, @Param("limit") int limit);

    List<Product> findOnSaleByTenantId(@Param("tenantId") String tenantId);

    List<Product> findInStockByTenantId(@Param("tenantId") String tenantId);

    List<Product> searchByNameAndTenantId(@Param("search") String search, @Param("tenantId") String tenantId);

    List<Product> findByMinRatingAndTenantId(@Param("minRating") Double minRating, @Param("tenantId") String tenantId);

    long countByTenantId(@Param("tenantId") String tenantId);

    long countOutOfStockByTenantId(@Param("tenantId") String tenantId);

    // New comprehensive filter method
    List<Product> findProductsWithFilters(@Param("filter") ProductFilterRequest filter, @Param("tenantId") String tenantId);

    long countProductsWithFilters(@Param("filter") ProductFilterRequest filter, @Param("tenantId") String tenantId);

    List<String> findAllCategoriesByTenantId(@Param("tenantId") String tenantId);

    BigDecimal findMinPriceByTenantId(@Param("tenantId") String tenantId);

    BigDecimal findMaxPriceByTenantId(@Param("tenantId") String tenantId);
}
