package com.homebase.ecom.mybatis.dashboard;

public class Category {
    private String categoryId;
    private String categoryName;
    private String thumbnail;

    public String getCategoryId() {
        return categoryId;
    }

    public Category setCategoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Category setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Category setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }
}
