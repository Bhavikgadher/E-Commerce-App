package com.example.myapp;

public class CategoryModel {

    private String categoryName;
    private String CategoryIcon;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryIcon() {
        return CategoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        CategoryIcon = categoryIcon;
    }

    public CategoryModel(String categoryName, String categoryIcon) {
        this.categoryName = categoryName;
        this.CategoryIcon = categoryIcon;
    }

}
