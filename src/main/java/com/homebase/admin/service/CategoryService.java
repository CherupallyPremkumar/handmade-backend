package com.homebase.admin.service;

import com.homebase.admin.dto.CategoryDTO;
import com.homebase.admin.entity.Category;
import com.homebase.admin.config.TenantContext;
import com.homebase.admin.repository.CategoryRepository;
import com.homebase.admin.repository.ProductRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<CategoryDTO> getAllCategories() {
        String tenantId = TenantContext.getCurrentTenant();
        return categoryRepository.findByTenantId(tenantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<String> getAllTags() {
        String tenantId = TenantContext.getCurrentTenant();
        System.out.println("CategoryService.getAllTags() - TenantId: " + tenantId);
        List<String> tags = productRepository.findByTenantId(tenantId).stream()
                .flatMap(product -> product.getTags().stream())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("CategoryService.getAllTags() - Found " + tags.size() + " unique tags: " + tags);
        return tags;
    }

    @Transactional
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = convertToEntity(categoryDTO);
        Category saved = categoryRepository.save(category);
        return convertToDTO(saved);
    }

    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        String tenantId = TenantContext.getCurrentTenant();
        Category category = categoryRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        
        category.setName(categoryDTO.getName());
        category.setSlug(categoryDTO.getSlug());
        category.setDescription(categoryDTO.getDescription());
        
        Category updated = categoryRepository.save(category);
        return convertToDTO(updated);
    }

    @Transactional
    public void deleteCategory(Long id) {
        String tenantId = TenantContext.getCurrentTenant();
        Category category = categoryRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        categoryRepository.delete(category);
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(String.valueOf(category.getId()));
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setDescription(category.getDescription());
        dto.setProductCount(category.getProductCount());
        return dto;
    }

    private Category convertToEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        category.setDescription(dto.getDescription());
        category.setProductCount(dto.getProductCount() != null ? dto.getProductCount() : 0);
        return category;
    }
}
