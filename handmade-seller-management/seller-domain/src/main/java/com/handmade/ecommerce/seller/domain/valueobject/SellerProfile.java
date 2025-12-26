package com.handmade.ecommerce.seller.domain.valueobject;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.net.URI;
import java.util.List;

/**
 * Seller's public profile information
 * Displayed to customers
 */
@Embeddable
public class SellerProfile implements Serializable {
    
    private String displayName;
    private String bio;
    private String profileImageUrl;
    private String coverImageUrl;
    private String tagline;
    
    // Social media links
    private String instagramHandle;
    private String facebookPage;
    private String websiteUrl;
    
    // Specialties stored as JSON
    private String specialtiesJson;  // ["Pottery", "Ceramics"]

    public SellerProfile() {
    }

    public SellerProfile(String displayName, String bio, String profileImageUrl) {
        this.displayName = displayName;
        this.bio = bio;
        this.profileImageUrl = profileImageUrl;
    }

    // Getters and Setters
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getInstagramHandle() {
        return instagramHandle;
    }

    public void setInstagramHandle(String instagramHandle) {
        this.instagramHandle = instagramHandle;
    }

    public String getFacebookPage() {
        return facebookPage;
    }

    public void setFacebookPage(String facebookPage) {
        this.facebookPage = facebookPage;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getSpecialtiesJson() {
        return specialtiesJson;
    }

    public void setSpecialtiesJson(String specialtiesJson) {
        this.specialtiesJson = specialtiesJson;
    }
}
