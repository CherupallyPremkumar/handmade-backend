package com.homebase.ecom.entity;

import com.homebase.ecom.entity.TenantConfiguration;
import com.homebase.ecom.entity.TenantTheme;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@Entity
@Table(name = "tenants",
        uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_code"}))
public class TenantEntity extends AbstractJpaStateEntity {

    @Column(name = "tenant_code", nullable = false, length = 50, unique = true)
    private String tenantCode;

    @Column(name = "tenant_name", nullable = false, length = 100)
    private String tenantName;

    @Column(name = "display_name", length = 100)
    private String displayName;

    @Column(name = "url_path", nullable = false, length = 100, unique = true)
    private String urlPath;

    @Column(name = "logo_url", length = 255)
    private String logoUrl;

    @Column(name = "currency", length = 10, nullable = false)
    private String currency;

    @Column(name = "locale", length = 10)
    private String locale;

    @Column(name = "timezone", length = 50)
    private String timezone;

    @Column(name = "support_email", length = 100)
    private String supportEmail;

    @Column(name = "support_phone", length = 20)
    private String supportPhone;

    @Embedded
    private TenantTheme theme;

    @Column(name = "configuration_id")
    private String configurationId;

    public TenantEntity() {

    }

    // Getters and Setters
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public TenantTheme getTheme() {
        return theme;
    }

    public void setTheme(TenantTheme theme) {
        this.theme = theme;
    }

    public String getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
    }

    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }

    public String getSupportPhone() {
        return supportPhone;
    }

    public void setSupportPhone(String supportPhone) {
        this.supportPhone = supportPhone;
    }
}
