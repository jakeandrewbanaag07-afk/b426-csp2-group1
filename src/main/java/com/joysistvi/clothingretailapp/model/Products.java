package com.joysistvi.clothingretailapp.model;

public class Products {
    private int id;
    private int categoryId;
    private String categoryName; // filled in when loading, only used for display
    private String name;
    private String description;
    private boolean archived;

    public Products(int id, String name, String description, int categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Products(int id, int categoryId, String name, String description, boolean archived) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.archived = archived;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
