package com.handmade.ecommerce.platform.dto;

import com.handmade.ecommerce.platform.domain.valueobject.BrandIdentity;
import java.io.Serializable;

public class UpdateBrandIdentityPayload implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public java.net.URI logoUrl;
    public java.net.URI faviconUrl;
    public String brandName;
    public String tagline;
    public String primaryColorHex;
    public String supportEmail;
    
    // Command metadata if needed
    public String initiatedBy;
}
