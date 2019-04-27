package com.example.dopewallpaper.Model;

public class WallpaperItem {
    public String imageUrl;
    public String category;
    public long viewCount;

    public WallpaperItem(){ }

    public WallpaperItem(String imageUrl, String category){
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

    public long getViewCount() { return viewCount; }

    public void setViewCount(long viewCount) { this.viewCount = viewCount; }
}
