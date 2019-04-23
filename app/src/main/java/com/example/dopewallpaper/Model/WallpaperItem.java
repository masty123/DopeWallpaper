package com.example.dopewallpaper.Model;

public class WallpaperItem {
    public String imageLink;
    public String categoryId;

    public WallpaperItem(){

    }

    public WallpaperItem(String imageLink, String categoryId){
        this.imageLink = imageLink;
        this.categoryId = categoryId;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
