package com.example.dopewallpaper.Model;

public class WallpaperItem {
    public String imageUrl;
    public String category;
    public String title;
    public long viewCount;

    public WallpaperItem(){ }

    public WallpaperItem(String imageUrl, String category, String title){
        this.imageUrl = imageUrl;
        this.category = category;
        this.title = title;
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

    public long getViewCount() { return viewCount; }

    public void setViewCount(long viewCount) { this.viewCount = viewCount; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
