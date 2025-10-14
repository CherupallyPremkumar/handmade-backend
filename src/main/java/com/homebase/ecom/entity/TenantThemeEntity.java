package com.homebase.ecom.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
public class TenantThemeEntity extends BaseJpaEntity {

    private String primaryColor;
    private String secondaryColor;
    private String background;
    private String text;
    private String fontFamily;
    private String buttonStyle;
    private String linkStyle;

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
