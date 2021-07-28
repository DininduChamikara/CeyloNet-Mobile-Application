package com.dcpappsolution.incotest2.models;

import java.io.Serializable;

public class SellerFavouriteModel implements Serializable {
    String titleNeed;
    String price;
    String currentDate;
    String currentTime;


    String description;
    String rating;
    String type;

    String documentId;

    public SellerFavouriteModel() {
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public SellerFavouriteModel(String titleNeed, String price, String currentDate, String currentTime, String description, String rating, String type) {
        this.titleNeed = titleNeed;
        this.price = price;
        this.currentDate = currentDate;
        this.currentTime = currentTime;
        this.description = description;
        this.rating = rating;
        this.type = type;
    }

    public String getTitleNeed() {
        return titleNeed;
    }

    public void setTitleNeed(String titleNeed) {
        this.titleNeed = titleNeed;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
