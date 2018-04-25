package com.valueservice.djs.db.entity.mini;

import java.util.Date;

public class MiniSign {
    private Long signId;

    private Long miniUserId;

    private Date lastSignTime;

    private Integer countDays;

    private Integer signDays;

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
}