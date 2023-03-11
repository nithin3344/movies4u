package com.example.movies4u;

public class Suggestions_model {
    String Title,Description,turl;

    Suggestions_model(){

    }

    public Suggestions_model(String title, String description, String turl) {
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
