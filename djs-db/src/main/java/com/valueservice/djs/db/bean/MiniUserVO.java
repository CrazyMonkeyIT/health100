package com.valueservice.djs.db.bean;

/**
 * Created by weiying on 2018/4/20.
 */
public class MiniUserVO {
    private Integer userId;
    private String nickName;
    private String avatar;
    private String badge1;  //一级徽章
    private String badge2;
    private String badge3;
    private String badge4;
    private int signDay;
    private String corpsName;
    private Long corpsPoint;
    private Long point;
    private Integer signCountDay;
    private Long corpsId;

    private Boolean oneSign;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getOneSign() {
        return oneSign;
    }

    public void setOneSign(Boolean oneSign) {
        this.oneSign = oneSign;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Integer getSignCountDay() {
        return signCountDay;
    }

    public void setSignCountDay(Integer signCountDay) {
        this.signCountDay = signCountDay;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBadge1() {
        return badge1;
    }

    public void setBadge1(String badge1) {
        this.badge1 = badge1;
    }

    public String getBadge2() {
        return badge2;
    }

    public void setBadge2(String badge2) {
        this.badge2 = badge2;
    }

    public String getBadge3() {
        return badge3;
    }

    public void setBadge3(String badge3) {
        this.badge3 = badge3;
    }

    public String getBadge4() {
        return badge4;
    }

    public void setBadge4(String badge4) {
        this.badge4 = badge4;
    }

    public int getSignDay() {
        return signDay;
    }

    public void setSignDay(int signDay) {
        this.signDay = signDay;
    }

    public String getCorpsName() {
        return corpsName;
    }

    public void setCorpsName(String corpsName) {
        this.corpsName = corpsName;
    }

    public Long getCorpsPoint() {
        return corpsPoint;
    }

    public void setCorpsPoint(Long corpsPoint) {
        this.corpsPoint = corpsPoint;
    }

    public Long getCorpsId() {
        return corpsId;
    }

    public void setCorpsId(Long corpsId) {
        this.corpsId = corpsId;
    }
}
