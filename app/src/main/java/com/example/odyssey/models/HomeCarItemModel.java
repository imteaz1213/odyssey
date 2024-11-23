package com.example.odyssey.models;

public class HomeCarItemModel {
    private String title;
    private String image;

    public HomeCarItemModel(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

}
