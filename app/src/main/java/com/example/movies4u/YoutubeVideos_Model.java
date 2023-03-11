package com.example.movies4u;

public class YoutubeVideos_Model {
    String Title,image_url,link;

    public YoutubeVideos_Model(String title, String image_url, String link) {
        Title = title;
        this.image_url = image_url;
        this.link = link;
    }

    public YoutubeVideos_Model() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
