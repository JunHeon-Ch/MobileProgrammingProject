package com.example.mp_termproject.ourcloset.dto;

public class SenderDTO {
    // 사진 url 이름 주소 핸드폰

    String imgUrl;
    String name;
    String address;
    String phone;

    public SenderDTO(String imgUrl, String name, String address, String phone) {

        this.imgUrl = imgUrl;
        this.name = name;
        this.address = address;
        this.phone = phone;

    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
