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
    Integer imgNum;

    public ImageDTO(){

    }
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

    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("userID", userID);
//        result.put("imgURL", imgURL);
//        result.put("category", category);
//        result.put("itemName", itemName);
//        result.put("color", color);
//        result.put("brand", brand);
//        result.put("season", season);
//        result.put("size", size);
//        result.put("shared", shared);
//
//        return result;
//    }

//    private void writeNewPost(FirebaseFirestore db,String id, String url, String userID, String imgURL, String category, String itemName, String color, String brand, String season, String size, String shared) {
//        // Create new post at /user-posts/$userid/$postid and at
//        // /posts/$postid simultaneously
//        DatabaseReference mDatabase;
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        String key = db.child("users").push().getKey();
//        ImageDTO img = new ImageDTO(id, url, userID, imgURL, category, itemName, color, brand, season, size, shared) {
//            Map<String, Object> imgValue = img.toMap();
//
//            Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/posts/"+key, imgValue);
//        childUpdates.put("/user-posts/"+userId +"/"+key,imgValue);
//
//        mDatabase.updateChildren(childUpdates);
//        };
//    }

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
