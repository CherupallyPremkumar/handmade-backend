package com.homebase.admin.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tags",
       uniqueConstraints = @UniqueConstraint(columnNames = {"slug", "tenant_id"}))

public class Tag extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String slug;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
