package com.handmade.ecommerce.platform.infrastructure.persistence.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 * JPA Embeddable for BrandIdentity.
 * Infrastructure layer - persistence model.
 */
@Embeddable
public class BrandIdentityEmbeddable implements Serializable {
    
    @Column(name = "brand_logo_url")
    private String logoUrl;
    
    @Column(name = "brand_favicon_url")
    private String faviconUrl;
    
    @Column(name = "brand_primary_color")
    private String primaryColorHex;
    
    @Column(name = "brand_support_email")
    private String supportEmail;

    protected BrandIdentityEmbeddable() {}

    public BrandIdentityEmbeddable(String logoUrl, String faviconUrl, String primaryColorHex, String supportEmail) {
        this.logoUrl = logoUrl;
        this.faviconUrl = faviconUrl;
        this.primaryColorHex = primaryColorHex;
        this.supportEmail = supportEmail;
    }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public String getFaviconUrl() { return faviconUrl; }
    public void setFaviconUrl(String faviconUrl) { this.faviconUrl = faviconUrl; }

    public String getPrimaryColorHex() { return primaryColorHex; }
    public void setPrimaryColorHex(String primaryColorHex) { this.primaryColorHex = primaryColorHex; }

    public String getSupportEmail() { return supportEmail; }
    public void setSupportEmail(String supportEmail) { this.supportEmail = supportEmail; }
}
