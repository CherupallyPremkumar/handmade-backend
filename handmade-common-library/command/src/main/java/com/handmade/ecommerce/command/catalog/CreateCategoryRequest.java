package com.handmade.ecommerce.command.catalog;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCategoryRequest {
    
    @NotBlank(message = "Category name is required")
    @Size(max = 100)
    private String name;
    
    @NotBlank(message = "Slug is required")
    @Size(max = 100)
    private String slug;
    
    private Long parentCategoryId;
    private String description;
    private String imageUrl;
    private Integer displayOrder;
    private Boolean isActive;
}
