package com.homebase.admin.mapper;

import com.homebase.admin.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * MyBatis Mapper for Product queries
 */
@Mapper
public interface ProductMapper {

    /**
     * Get low stock products
     */
    @Select("SELECT * FROM products " +
            "WHERE tenant_id = #{tenantId} " +
            "AND stock < #{threshold} " +
            "AND stock > 0 " +
            "ORDER BY stock ASC")
    List<Product> getLowStockProducts(
            @Param("tenantId") String tenantId,
            @Param("threshold") int threshold
    );

    /**
     * Get out of stock products
     */
    @Select("SELECT * FROM products " +
            "WHERE tenant_id = #{tenantId} " +
            "AND stock = 0 " +
            "ORDER BY updated_at DESC")
    List<Product> getOutOfStockProducts(@Param("tenantId") String tenantId);

    /**
     * Get products by category with stock info
     */
    @Select("SELECT " +
            "c.name as category_name, " +
            "COUNT(p.id) as product_count, " +
            "SUM(p.stock) as total_stock, " +
            "AVG(p.price) as average_price " +
            "FROM products p " +
            "JOIN categories c ON p.category_id = c.id " +
            "WHERE p.tenant_id = #{tenantId} " +
            "GROUP BY c.id, c.name " +
            "ORDER BY product_count DESC")
    List<Map<String, Object>> getProductsByCategory(@Param("tenantId") String tenantId);

    /**
     * Search products with filters
     */
    @Select("<script>" +
            "SELECT * FROM products " +
            "WHERE tenant_id = #{tenantId} " +
            "<if test='categoryId != null'> AND category_id = #{categoryId} </if>" +
            "<if test='minPrice != null'> AND price >= #{minPrice} </if>" +
            "<if test='maxPrice != null'> AND price <= #{maxPrice} </if>" +
            "<if test='onSale != null'> AND on_sale = #{onSale} </if>" +
            "<if test='searchTerm != null'> " +
            "AND (name LIKE CONCAT('%', #{searchTerm}, '%') " +
            "OR description LIKE CONCAT('%', #{searchTerm}, '%'))" +
            "</if>" +
            "ORDER BY " +
            "<choose>" +
            "<when test='sortBy == \"price_asc\"'> price ASC </when>" +
            "<when test='sortBy == \"price_desc\"'> price DESC </when>" +
            "<when test='sortBy == \"name\"'> name ASC </when>" +
            "<otherwise> created_at DESC </otherwise>" +
            "</choose>" +
            "</script>")
    List<Product> searchProducts(
            @Param("tenantId") String tenantId,
            @Param("categoryId") Long categoryId,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            @Param("onSale") Boolean onSale,
            @Param("searchTerm") String searchTerm,
            @Param("sortBy") String sortBy
    );

    /**
     * Bulk update stock
     */
    @Update("<script>" +
            "UPDATE products SET stock = stock + #{adjustment} " +
            "WHERE id IN " +
            "<foreach item='id' collection='productIds' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "AND tenant_id = #{tenantId}" +
            "</script>")
    int bulkUpdateStock(
            @Param("tenantId") String tenantId,
            @Param("productIds") List<Long> productIds,
            @Param("adjustment") int adjustment
    );
}
