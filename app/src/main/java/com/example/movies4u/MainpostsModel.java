package com.example.movies4u;

public class MainpostsModel {
    String Title,Description,turl;

    MainpostsModel(){

    }

    public MainpostsModel(String title, String description, String turl) {
        Title = title;
        Description = description;
        this.turl = turl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTurl() {
        return turl;
    }

    public void setTurl(String turl) {
        this.turl = turl;
    }
}
