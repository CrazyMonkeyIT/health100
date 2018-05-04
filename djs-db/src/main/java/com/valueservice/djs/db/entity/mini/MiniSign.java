package com.valueservice.djs.db.entity.mini;

import java.util.Date;

public class MiniSign {
    private Long signId;

    private Long miniUserId;

    private Date lastSignTime;

    private Integer countDays;

    private Integer signDays;

    private Integer countDays7;

    private Integer countDays30;

    private Integer countDays60;

    private Integer countDays100;

    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
    }

    public Long getMiniUserId() {
        return miniUserId;
    }

    public void setMiniUserId(Long miniUserId) {
        this.miniUserId = miniUserId;
    }

    public Date getLastSignTime() {
        return lastSignTime;
    }

    public void setLastSignTime(Date lastSignTime) {
        this.lastSignTime = lastSignTime;
    }

    public Integer getCountDays() {
        return countDays;
    }

    public void setCountDays(Integer countDays) {
        this.countDays = countDays;
    }


    public Integer getSignDays() {
        return signDays;
    }

    public void setSignDays(Integer signDays) {
        this.signDays = signDays;
    }


    public Integer getCountDays7() {
        return countDays7;
    }

    public void setCountDays7(Integer countDays7) {
        this.countDays7 = countDays7;
    }

    public Integer getCountDays30() {
        return countDays30;
    }

    public void setCountDays30(Integer countDays30) {
        this.countDays30 = countDays30;
    }

    public Integer getCountDays60() {
        return countDays60;
    }

    public void setCountDays60(Integer countDays60) {
        this.countDays60 = countDays60;
    }

    public Integer getCountDays100() {
        return countDays100;
    }

    public void setCountDays100(Integer countDays100) {
        this.countDays100 = countDays100;
    }
}