package com.example.mp_termproject.ourcloset.dto;

public class ResponseDTO {

    String senderName;
    String senderAge;
    String senderAddress;
    String senderPhoneNumber;
    Double senderLatitude;
    Double senderLongitude;
    String url;

    public ResponseDTO(String senderName, String senderAge, String senderAddress, String senderPhoneNumber, Double senderLatitude, Double senderLongitude, String url) {
        this.senderName = senderName;
        this.senderAge = senderAge;
        this.senderAddress = senderAddress;
        this.senderPhoneNumber = senderPhoneNumber;
        this.senderLatitude = senderLatitude;
        this.senderLongitude = senderLongitude;
        this.url = url;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderAge() {
        return senderAge;
    }

    public void setSenderAge(String senderAge) {
        this.senderAge = senderAge;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public String getSenderPhoneNumber() {
        return senderPhoneNumber;
    }

    public void setSenderPhoneNumber(String senderPhoneNumber) {
        this.senderPhoneNumber = senderPhoneNumber;
    }

    public Double getSenderLatitude() {
        return senderLatitude;
    }

    public void setSenderLatitude(Double senderLatitude) {
        this.senderLatitude = senderLatitude;
    }

    public Double getSenderLongitude() {
        return senderLongitude;
    }

    public void setSenderLongitude(Double senderLongitude) {
        this.senderLongitude = senderLongitude;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
