package com.example.movies4u.Models;

public class UserAccountSettings {
    private String username;
    private String display_name;

    public UserAccountSettings(String username, String display_name) {
        this.username = username;
        this.display_name = display_name;
    }


    public UserAccountSettings() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplay_name() {
        return display_name;
    }

    public void setDisplay_name(String display_name) {
        this.display_name = display_name;
    }

    @Override
    public String toString() {
        return "UserAccountSettings{" +
                "username='" + username + '\'' +
                ", display_name='" + display_name + '\'' +
                '}';
    }
}
