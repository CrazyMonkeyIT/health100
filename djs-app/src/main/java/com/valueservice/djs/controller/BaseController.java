package com.valueservice.djs.controller;

import com.valueservice.djs.db.entity.system.UserInfoDO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 获得当前用户
     * @return
     */
    public UserInfoDO getCurrentUser(){
        UserInfoDO user = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            if(subject != null && subject.getPrincipal() != null) {
                user = (UserInfoDO) subject.getSession().getAttribute("currUser");
            }
        } catch (Exception e) {
            logger.error("获得当前用户异常",e);
        }
        return user;
    }
}
