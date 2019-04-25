package com.example.dopewallpaper.Database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.support.annotation.NonNull;


@Entity(tableName = "recents", primaryKeys = {"imageUrl", "category"})
public class Recents {
    @ColumnInfo(name = "imageUrl")
    @NonNull
    private String imageUrl ;


    @ColumnInfo(name = "category")
    @NonNull
    private String category ;

    @ColumnInfo(name = "saveTime")
    private String saveTime;

    public Recents(){}

    public Recents(String imageUrl, String category, String saveTime) {
        this.imageUrl = imageUrl;
        this.category = category;
        this.saveTime = saveTime;
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
}
