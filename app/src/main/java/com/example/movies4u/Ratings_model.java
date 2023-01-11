package com.example.movies4u;

public class Ratings_model {
    String Title,Description,imgurl,timestamp;

    public Ratings_model(String title, String description, String imgurl, String timestamp) {
        Title = title;
        Description = description;
        this.imgurl = imgurl;
        this.timestamp = timestamp;
    }


    public Ratings_model() {
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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getTimestamp() {return timestamp;}

    public void setTimestamp(String timestamp) {this.timestamp = timestamp;}
}
