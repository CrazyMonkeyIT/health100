package com.valueservice.djs.db.entity.mini;

import java.util.Date;

public class MiniCorps {
    private Long corpsId;

    private String corpsName;

    private String corpsHeadImage;

    private String corpsBannerImage;

    private Long point;

    private String corpsIntroduce;

    private Long isTop;

    private Long isSpecial;  //0：正常 1：不参与全局比较

    public Long getCorpsId() {
        return corpsId;
    }

    public void setCorpsId(Long corpsId) {
        this.corpsId = corpsId;
    }

    public String getCorpsName() {
        return corpsName;
    }

    public void setCorpsName(String corpsName) {
        this.corpsName = corpsName == null ? null : corpsName.trim();
    }

    public String getCorpsHeadImage() {
        return corpsHeadImage;
    }

    public void setCorpsHeadImage(String corpsHeadImage) {
        this.corpsHeadImage = corpsHeadImage == null ? null : corpsHeadImage.trim();
    }

    public String getCorpsBannerImage() {
        return corpsBannerImage;
    }

    public void setCorpsBannerImage(String corpsBannerImage) {
        this.corpsBannerImage = corpsBannerImage == null ? null : corpsBannerImage.trim();
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public String getCorpsIntroduce() {
        return corpsIntroduce;
    }

    public void setCorpsIntroduce(String corpsIntroduce) {
        this.corpsIntroduce = corpsIntroduce;
    }

    public Long getIsTop() {
        return isTop;
    }

    public void setIsTop(Long isTop) {
        this.isTop = isTop;
    }

    public Long getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Long isSpecial) {
        this.isSpecial = isSpecial;
    }
}