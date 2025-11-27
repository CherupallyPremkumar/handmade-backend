package com.handmade.ecommerce.command.dto.content;

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
public class CreatePageRequest {
    
    @NotBlank(message = "Page title is required")
    @Size(max = 200)
    private String title;
    
    @NotBlank(message = "Slug is required")
    @Size(max = 200)
    private String slug;
    
    @NotBlank(message = "Content is required")
    private String content;
    
    private String metaTitle;
    private String metaDescription;
    private String metaKeywords;
    private Boolean isPublished;
}
