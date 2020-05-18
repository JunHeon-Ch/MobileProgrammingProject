package com.example.mp_termproject.mycloset;

public class ImageDTO {
    String userID;
    String imgURL;
    String category;
    String itemName;
    String color;
    String brand;
    String season;
    String size;
    String shared;

    public ImageDTO(String userID, String imgURL, String category, String itemName, String color, String brand, String season, String size, String shared) {
        this.userID = userID;
        this.imgURL = imgURL;
        this.category = category;
        this.itemName = itemName;
        this.color = color;
        this.brand = brand;
        this.season = season;
        this.size = size;
        this.shared = shared;
    }

    public String getUserID() {
        return userID;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getCategory() {
        return category;
    }

    public String getItemName() {
        return itemName;
    }

    public String getColor() {
        return color;
    }

    public String getBrand() {
        return brand;
    }

    public String getSeason() {
        return season;
    }

    public String getSize() {
        return size;
    }

    public String getShared() {
        return shared;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setShared(String shared) {
        this.shared = shared;
    }
}
