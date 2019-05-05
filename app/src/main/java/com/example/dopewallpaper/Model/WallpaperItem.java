package com.example.dopewallpaper.Model;

import com.google.firebase.database.Exclude;

public class WallpaperItem {
    @Exclude
    public String id;

    public String imageUrl;
    public String category;
    public String title;
    public String author;
    public long viewCount;


    public WallpaperItem(){ }

    public WallpaperItem(String imageUrl, String category, String title, String author){
        this.imageUrl = imageUrl;
        this.category = category;
        this.title = title;
        this.author = author;
    }


//    public WallpaperItem(String id, String imageUrl, String category, String title, String author) {
//        this.id = id;
//        this.imageUrl = imageUrl;
//        this.category = category;
//        this.title = title;
//        this.author = author;
//    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setImageLink(String imageLink) {
        this.imageUrl = imageLink;
    }

    public void setCategoryId(String categoryId) {
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


    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() { return author; }
}
