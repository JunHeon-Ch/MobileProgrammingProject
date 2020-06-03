package com.example.mp_termproject.mycloset;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
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
    Double imgNum;

    public ImageDTO() {

    }

    public ImageDTO(String userID, String imgURL, String category, String itemName, String color, String brand, String season, String size, String shared, Double imgNum) {
        this.userID = userID;
        this.imgURL = imgURL;
        this.category = category;
        this.itemName = itemName;
        this.color = color;
        this.brand = brand;
        this.season = season;
        this.size = size;
        this.shared = shared;
        this.imgNum = imgNum;
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

    public Double getImgNum() {
        return imgNum;
    }

    public void setImgNum(Double imgNum) {
        this.imgNum = imgNum;
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
