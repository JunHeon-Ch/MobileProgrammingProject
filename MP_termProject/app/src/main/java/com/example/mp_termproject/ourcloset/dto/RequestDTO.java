package com.example.mp_termproject.ourcloset.dto;

public class RequestDTO {

    String targetName;
    String targetAge;
    String targetAddress;
    String targetPhoneNumber;
    Double targetLatitude;
    Double targetLongitude;
    String url;

    public RequestDTO(String targetName, String targetAge, String targetAddress, String targetPhoneNumber, Double targetLatitude, Double targetLongitude, String url) {
        this.targetName = targetName;
        this.targetAge = targetAge;
        this.targetAddress = targetAddress;
        this.targetPhoneNumber = targetPhoneNumber;
        this.targetLatitude = targetLatitude;
        this.targetLongitude = targetLongitude;
        this.url = url;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetAge() {
        return targetAge;
    }

    public void setTargetAge(String targetAge) {
        this.targetAge = targetAge;
    }

    public String getTargetAddress() {
        return targetAddress;
    }

    public void setTargetAddress(String targetAddress) {
        this.targetAddress = targetAddress;
    }

    public String getTargetPhoneNumber() {
        return targetPhoneNumber;
    }

    public void setTargetPhoneNumber(String targetPhoneNumber) {
        this.targetPhoneNumber = targetPhoneNumber;
    }

    public Double getTargetLatitude() {
        return targetLatitude;
    }

    public void setTargetLatitude(Double targetLatitude) {
        this.targetLatitude = targetLatitude;
    }

    public Double getTargetLongitude() {
        return targetLongitude;
    }

    public void setTargetLongitude(Double targetLongitude) {
        this.targetLongitude = targetLongitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}