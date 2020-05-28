package com.example.mp_termproject.lookbook;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class LookbookDTO {
    String userID;
    String imgURL;
    String occasion;
    String season;

    public LookbookDTO(String userID, String imgURL, String occasion, String season) {
        this.userID = userID;
        this.imgURL = imgURL;
        this.occasion = occasion;
        this.season = season;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getOccasion() {
        return occasion;
    }

    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
