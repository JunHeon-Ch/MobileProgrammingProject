package com.example.mp_termproject.signup;

public class UserInfo {
    private String name;
    private String phoneNumber;
    private String birthDay;
    private String address;
    private Double imgNum;
    private Double lookNum;
    private Double latitude;
    private Double longitude;

    public UserInfo(String name, String phoneNumber, String birthDay, String address,
                    Double imgNum, Double lookNum, Double latitude, Double longitude) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.address = address;
        this.imgNum = imgNum;
        this.lookNum = lookNum;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDay() {
        return this.birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getImgNum() {
        return this.imgNum;
    }

    public void setImgNum(Double imgNum) {
        this.imgNum = imgNum;
    }

    public Double getLookNum() {
        return lookNum;
    }

    public void setLookNum(Double lookNum) {
        this.lookNum = lookNum;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}