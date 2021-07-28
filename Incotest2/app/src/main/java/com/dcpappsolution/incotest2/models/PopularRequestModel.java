package com.dcpappsolution.incotest2.models;

import java.io.Serializable;

public class PopularRequestModel implements Serializable {
    String titleNeed;
    String description;
    String rating;
    String price;
    String type;

    public PopularRequestModel() {
    }

    public PopularRequestModel(String titleNeed, String description, String rating, String price, String type) {
        this.titleNeed = titleNeed;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.type = type;
    }

    public String getTitleNeed() {
        return titleNeed;
    }

    public void setTitleNeed(String titleNeed) {
        this.titleNeed = titleNeed;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
