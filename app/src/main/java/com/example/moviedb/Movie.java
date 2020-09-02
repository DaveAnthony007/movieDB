package com.example.moviedb;

import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private String year;
    private String desc;
    private String image_url;

    public Movie() {
        title = "";
        year = "";
        desc = "";
        image_url = "";
    }

    public Movie(String title, String year, String desc, String image_url) {
        this.title = title;
        this.year = year;
        this.desc = desc;
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
