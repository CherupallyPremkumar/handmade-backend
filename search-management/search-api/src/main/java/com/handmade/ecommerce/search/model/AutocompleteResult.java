package com.handmade.ecommerce.search.model;

import java.util.ArrayList;
import java.util.List;

/**
 * AutocompleteResult - Autocomplete suggestions like Amazon search bar
 */
public class AutocompleteResult {
    
    private String query;
    private List<Suggestion> suggestions;
    private List<ProductSuggestion> products;
    private List<CategorySuggestion> categories;
    
    public AutocompleteResult() {
        this.suggestions = new ArrayList<>();
        this.products = new ArrayList<>();
        this.categories = new ArrayList<>();
    }
    
    public String getQuery() {
        return query;
    }
    
    public void setQuery(String query) {
        this.query = query;
    }
    
    public List<Suggestion> getSuggestions() {
        return suggestions;
    }
    
    public void setSuggestions(List<Suggestion> suggestions) {
        this.suggestions = suggestions;
    }
    
    public void addSuggestion(Suggestion suggestion) {
        this.suggestions.add(suggestion);
    }
    
    public List<ProductSuggestion> getProducts() {
        return products;
    }
    
    public void setProducts(List<ProductSuggestion> products) {
        this.products = products;
    }
    
    public void addProduct(ProductSuggestion product) {
        this.products.add(product);
    }
    
    public List<CategorySuggestion> getCategories() {
        return categories;
    }
    
    public void setCategories(List<CategorySuggestion> categories) {
        this.categories = categories;
    }
    
    public void addCategory(CategorySuggestion category) {
        this.categories.add(category);
    }
    
    /**
     * Suggestion - Text suggestion
     */
    public static class Suggestion {
        private String text;
        private double score;
        private String highlighted;
        
        public Suggestion() {}
        
        public Suggestion(String text, double score) {
            this.text = text;
            this.score = score;
        }
        
        public String getText() { return text; }
        public void setText(String text) { this.text = text; }
        public double getScore() { return score; }
        public void setScore(double score) { this.score = score; }
        public String getHighlighted() { return highlighted; }
        public void setHighlighted(String highlighted) { this.highlighted = highlighted; }
    }
    
    /**
     * ProductSuggestion - Product suggestion with image
     */
    public static class ProductSuggestion {
        private String productId;
        private String name;
        private String imageUrl;
        private String price;
        private Double rating;
        
        public ProductSuggestion() {}
        
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        public String getPrice() { return price; }
        public void setPrice(String price) { this.price = price; }
        public Double getRating() { return rating; }
        public void setRating(Double rating) { this.rating = rating; }
    }
    
    /**
     * CategorySuggestion - Category suggestion
     */
    public static class CategorySuggestion {
        private String categoryId;
        private String name;
        private int productCount;
        
        public CategorySuggestion() {}
        
        public String getCategoryId() { return categoryId; }
        public void setCategoryId(String categoryId) { this.categoryId = categoryId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getProductCount() { return productCount; }
        public void setProductCount(int productCount) { this.productCount = productCount; }
    }
}
