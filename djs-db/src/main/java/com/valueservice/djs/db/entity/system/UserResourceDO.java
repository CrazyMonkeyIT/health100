package com.valueservice.djs.db.entity.system;

import java.util.Date;

public class UserResourceDO {
    private Long userResourceId;

    private Long userId;

    private Long resourceId;

    private Integer active;

    private Date createTime;

    private Date updateTime;

    public Long getUserResourceId() {
        return userResourceId;
    }

    public void setUserResourceId(Long userResourceId) {
        this.userResourceId = userResourceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}