package com.homebase.admin.dto;

import com.homebase.admin.entity.TenantTheme;
import org.springframework.ui.context.Theme;

public class TenantConfigDTO {
    private String id;
    private String name;
    private String displayName;
    private String logoUrl;
    private TenantTheme theme;
    private String domain;
    private String urlPath; // e.g. "acme-corp"
    private String currency; // e.g. "USD"
    private TenantConfigurationDTO configuration;

    // Private constructor to enforce builder usage
    public TenantConfigDTO(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.displayName = builder.displayName;
        this.logoUrl = builder.logoUrl;
        this.theme = builder.theme;
        this.domain = builder.domain;
        this.urlPath = builder.urlPath;
        this.currency = builder.currency;
        this.configuration = builder.configuration;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDisplayName() { return displayName; }
    public String getLogoUrl() { return logoUrl; }
    public TenantTheme getTheme() { return theme; }
    public String getDomain() { return domain; }
    public String getUrlPath() { return urlPath; }
    public String getCurrency() { return currency; }
    public TenantConfigurationDTO getConfiguration() { return configuration; }

    // Builder class
    public static class Builder {
        private String id;
        private String name;
        private String displayName;
        private String logoUrl;
        private TenantTheme theme;
        private String domain;
        private String urlPath;
        private String currency;
        private TenantConfigurationDTO configuration;

        public Builder id(String id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder displayName(String displayName) { this.displayName = displayName; return this; }
        public Builder logoUrl(String logoUrl) { this.logoUrl = logoUrl; return this; }
        public Builder theme(TenantTheme theme) { this.theme = theme; return this; }
        public Builder domain(String domain) { this.domain = domain; return this; }
        public Builder urlPath(String urlPath) { this.urlPath = urlPath; return this; }
        public Builder currency(String currency) { this.currency = currency; return this; }
        public Builder configuration(TenantConfigurationDTO configuration) { this.configuration = configuration; return this; }

        public TenantConfigDTO build() {
            return new TenantConfigDTO(this);
        }
    }
}