package com.example.mp_termproject.ourcloset.dto;

public class RequestDTO {

    String imgNum;
    String reqNum;

    public RequestDTO(String imgNum,String reqNum) {
        this.imgNum=imgNum;
        this.reqNum=reqNum;
    }

    public String getImgNum() {
        return imgNum;
    }
    public void setImgNum(String imgNum ) {
        this.imgNum = imgNum;
    }

    public String getReqNum() {
        return reqNum;
    }
    public void setReqNum(String reqNum ) {
        this.reqNum = reqNum;
    }

}
