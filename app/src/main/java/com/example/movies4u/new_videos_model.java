package com.example.movies4u;

public class new_videos_model {
    String Title,Image_url,link;

    new_videos_model(){

    }

    public new_videos_model(String title, String image_url, String link) {
        Title = title;
        Image_url = image_url;
        this.link = link;
    }

    public String getImage_url() {
        return Image_url;
    }

    public String getLink() {
        return link;
    }

    public void setImage_url(String image_url) {
        this.Image_url = image_url;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
