package com.handmade.ecommerce.catalog.service.impl;

import com.handmade.ecommerce.catalog.model.Category;
import com.handmade.ecommerce.catalog.repository.CategoryRepository;
import com.handmade.ecommerce.catalog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    @Transactional
    public Category createCategory(Category category) {
        // Auto-calculate level based on parent
        if (category.getParentId() != null) {
            Optional<Category> parent = categoryRepository.findById(category.getParentId());
            if (parent.isPresent()) {
                category.setLevel(parent.get().getLevel() + 1);
            }
        } else {
            category.setLevel(0); // Root category
        }

        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(String id, Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));

        existing.setName(category.getName());
        existing.setSlug(category.getSlug());
        existing.setDescription(category.getDescription());
        existing.setDisplayOrder(category.getDisplayOrder());
        existing.setImageUrl(category.getImageUrl());
        existing.setIcon(category.getIcon());
        existing.setActive(category.getActive());
        existing.setFeatured(category.getFeatured());

        return categoryRepository.save(existing);
    }

    @Override
    public Optional<Category> getCategoryById(String id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> getRootCategories() {
        return categoryRepository.findRootCategories();
    }

    @Override
    public List<Category> getChildCategories(String parentId) {
        return categoryRepository.findByParentId(parentId);
    }

    @Override
    public List<Category> getCategoryTree() {
        List<Category> roots = getRootCategories();
        for (Category root : roots) {
            loadChildren(root);
        }
        return roots;
    }

    private void loadChildren(Category category) {
        List<Category> children = getChildCategories(category.getId());
        category.setChildren(children);
        for (Category child : children) {
            loadChildren(child); // Recursive
        }
    }

    @Override
    @Transactional
    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found: " + id));
        category.setActive(false); // Soft delete
        categoryRepository.save(category);
    }
}
