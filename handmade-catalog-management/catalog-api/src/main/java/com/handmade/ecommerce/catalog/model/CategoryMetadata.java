package com.handmade.ecommerce.catalog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * CategoryMetadata - Value Object
 * Additional metadata for categories
 */
@Embeddable
public class CategoryMetadata {
    
    @Column(name = "meta_title", length = 100)
    private String metaTitle; // SEO title
    
    @Column(name = "meta_description", length = 200)
    private String metaDescription; // SEO description
    
    @Column(name = "meta_keywords", length = 200)
    private String metaKeywords; // SEO keywords
    
    @Column(name = "banner_image_url", length = 500)
    private String bannerImageUrl; // Category page banner
    
    @Column(name = "show_in_menu")
    private Boolean showInMenu = true;
    
    @Column(name = "show_in_footer")
    private Boolean showInFooter = false;
    
    // Getters and Setters
    
    public String getMetaTitle() {
        return metaTitle;
    }
    
    public void setMetaTitle(String metaTitle) {
        this.metaTitle = metaTitle;
    }
    
    public String getMetaDescription() {
        return metaDescription;
    }
    
    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }
    
    public String getMetaKeywords() {
        return metaKeywords;
    }
    
    public void setMetaKeywords(String metaKeywords) {
        this.metaKeywords = metaKeywords;
    }
    
    public String getBannerImageUrl() {
        return bannerImageUrl;
    }
    
    public void setBannerImageUrl(String bannerImageUrl) {
        this.bannerImageUrl = bannerImageUrl;
    }
    
    public Boolean getShowInMenu() {
        return showInMenu;
    }
    
    public void setShowInMenu(Boolean showInMenu) {
        this.showInMenu = showInMenu;
    }
    
    public Boolean getShowInFooter() {
        return showInFooter;
    }
    
    public void setShowInFooter(Boolean showInFooter) {
        this.showInFooter = showInFooter;
    }
}
