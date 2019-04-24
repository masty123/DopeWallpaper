package com.example.dopewallpaper.Model;

public class WallpaperItem {
    public String imageUrl;
    public String category;

    public WallpaperItem(){

    }

    public WallpaperItem(String imageLink, String category){
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public String getImageLink() {
        return imageUrl;
    }

    public String getCategoryId() {
        return category;
    }

    public void setImageLink(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategoryId(String category) {
        this.category = category;
    }
}
