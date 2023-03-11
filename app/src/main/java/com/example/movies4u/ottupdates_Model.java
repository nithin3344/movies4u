package com.example.movies4u;

public class ottupdates_Model {
    String Title,Description,turl,date,platform;

    ottupdates_Model(){
    }

    public ottupdates_Model(String title, String description, String turl, String date, String platform) {
        Title = title;
        Description = description;
        this.turl = turl;
        this.date = date;
        this.platform = platform;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
