package com.liuyibo.lovebill.dao.entity;

import com.liuyibo.lovebill.utils.lang.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Map;

@Entity
public class BillUser {
    @Id
    private String userId;
    private String billId;
    private String nickName;
    private String avatarUrl;
    private String gender;
    private String city;
    private String province;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public BillUser(Map<String, Object> userInfo){
        this.setAvatarUrl((String) userInfo.get("avatarUrl"));
        this.setCity((String) userInfo.get("city"));
        this.setGender(String.valueOf(userInfo.get("gender")));
        this.setNickName((String) userInfo.get("nickName"));
        this.setProvince((String) userInfo.get("province"));
    }
    public BillUser(){}

}
