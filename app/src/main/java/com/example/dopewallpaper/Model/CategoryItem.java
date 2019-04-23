package com.example.dopewallpaper.Model;

public class CategoryItem {
    private String name;
    private String ImageLink;

    public CategoryItem(){

    }

    public CategoryItem(String name, String imagelink){
        this.name = name;
        this.ImageLink = imagelink;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
         return this.name;
    }

    public void setImagelink(String ImageLink){
        this.ImageLink = ImageLink;
    }

    public String getImagelink(){
        return this.ImageLink;
    }

}
