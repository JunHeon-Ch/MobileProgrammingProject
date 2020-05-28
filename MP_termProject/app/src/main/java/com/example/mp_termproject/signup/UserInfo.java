package com.example.mp_termproject.signup;

public class UserInfo {
    private String name;
    private String phoneNumber;
    private String birthDay;
    private String address;
    private Integer imgNum;
    private Integer lookNum;
    private Double Latitude;
    private Double Longitude;

    public UserInfo(String name, String phoneNumber, String birthDay, String address, Integer imgNum, Integer lookNum){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.address = address;
        this.imgNum = imgNum;
        this.lookNum = lookNum;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public String getBirthDay(){
        return this.birthDay;
    }
    public void setBirthDay(String birthDay){
        this.birthDay = birthDay;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public double getLatitude(){
        return this.Latitude;
    }
    public void setLatitude(Double Latitude){
        this.Latitude = Latitude;
    }
    public double getLongitude(){
        return this.Longitude;
    }
    public void setLongitude(Double Longitude){
        this.Longitude = Longitude;
    }


}