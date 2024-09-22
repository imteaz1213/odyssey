package com.example.odyssey.models;

public class HomeCarItemModel {
    private String title;
    private int image;

    public HomeCarItemModel(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

}
