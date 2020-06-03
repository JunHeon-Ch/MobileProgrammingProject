package com.example.mp_termproject.ourcloset.dto;

import com.example.mp_termproject.mycloset.dto.ImageDTO;
import com.example.mp_termproject.signup.UserInfo;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class InfoDTO {

    ImageDTO imageDTO;
    UserInfo userInfo;

    public InfoDTO(ImageDTO imageDTO, UserInfo userInfo) {
        this.imageDTO = imageDTO;
        this.userInfo = userInfo;
    }

    public ImageDTO getImageDTO() {
        return imageDTO;
    }

    public void setImageDTO(ImageDTO imageDTO) {
        this.imageDTO = imageDTO;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
