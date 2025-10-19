package com.homebase.ecom.domain;

import jakarta.persistence.Embeddable;
import org.chenile.utils.entity.model.BaseEntity;


public class TenantTheme extends BaseEntity {

    private String primaryColor = "#4A90E2";
    private String secondaryColor = "#50E3C2";
    private String background = "#FFFFFF";
    private String text = "#333333";

    // Getters and Setters
    public String getPrimaryColor() { return primaryColor; }
    public void setPrimaryColor(String primaryColor) { this.primaryColor = primaryColor; }

    public String getSecondaryColor() { return secondaryColor; }
    public void setSecondaryColor(String secondaryColor) { this.secondaryColor = secondaryColor; }

    public String getBackground() { return background; }
    public void setBackground(String background) { this.background = background; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
}
