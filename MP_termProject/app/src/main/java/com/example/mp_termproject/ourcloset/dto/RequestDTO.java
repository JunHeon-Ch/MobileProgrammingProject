package com.example.mp_termproject.ourcloset.dto;

public class RequestDTO {

    String targetUserId;
    String myName;
    String myAge;
    String myAddress;
    Double latitude;
    Double longitude;
    String url;


    public RequestDTO(String targetUserId, String myName, String myAge, String myAddress, Double latitude, Double longitude, String url) {
        this.targetUserId = targetUserId;
        this.myName = myName;
        this.myAge = myAge;
        this.myAddress = myAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.url = url;
    }

    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

    public String getMyAge() {
        return myAge;
    }

    public void setMyAge(String myAge) {
        this.myAge = myAge;
    }

    public String getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(String myAddress) {
        this.myAddress = myAddress;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
