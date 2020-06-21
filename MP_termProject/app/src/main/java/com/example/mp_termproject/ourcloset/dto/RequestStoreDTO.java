package com.example.mp_termproject.ourcloset.dto;

public class RequestStoreDTO {
    String sender;
    String receiver;
    String imgNum;
    String reqNum;

    public RequestStoreDTO(String sender, String receiver, String imgNum, String reqNum) {
        this.sender = sender;
        this.receiver = receiver;
        this.imgNum = imgNum;
        this.reqNum = reqNum;
    }

    public String getImgNum() {
        return imgNum;
    }

    public void setImgNum(String imgNum) {
        this.imgNum = imgNum;
    }

    public String getReqNum() {
        return reqNum;
    }

    public void setReqNum(String reqNum) {
        this.reqNum = reqNum;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
