package com.handmade.ecommerce.command.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
    
    private Long id;
    private String name;
    private String slug;
    private Long parentCategoryId;
    private String parentCategoryName;
    private String description;
    private String imageUrl;
    private Integer displayOrder;
    private Boolean isActive;
    private Integer productCount;
    private List<CategoryResponse> subCategories;
}
