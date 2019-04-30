package com.example.dopewallpaper.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;


@Entity(tableName = "recents", primaryKeys = {"imageUrl", "category", "title"})
public class Recents {
    @ColumnInfo(name = "imageUrl")
    @NonNull
    private String imageUrl ;


    @ColumnInfo(name = "category")
    @NonNull
    private String category ;

    @ColumnInfo(name = "saveTime")
    private String saveTime;

    @ColumnInfo(name = "key")
    private String key;



    @ColumnInfo(name = "title")
    @NonNull
    private String title;


//    public Recents(@NonNull String imageUrl, @NonNull String category, String saveTime, String key) {
//        this.imageUrl = imageUrl;
//        this.category = category;
//        this.saveTime = saveTime;
//        this.key = key;
//    }

    public Recents(@NonNull String imageUrl, @NonNull String category, String saveTime, String key, String title) {
        this.imageUrl = imageUrl;
        this.category = category;
        this.saveTime = saveTime;
        this.key = key;
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(@NonNull String imageUrl) {  this.imageUrl = imageUrl; }

    public String getCategory() {
        return category;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
