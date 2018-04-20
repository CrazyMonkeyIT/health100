package com.valueservice.djs.service.system;

import com.valueservice.djs.bean.BaseResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.valueservice.djs.db.dao.system.UserInfoDOMapper;
import com.valueservice.djs.db.dao.system.UserResourceDOMapper;
import com.valueservice.djs.db.entity.system.UserInfoDO;
import com.valueservice.djs.db.entity.system.UserResourceDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.valueservice.djs.util.SHA256;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class UserInfoService {

    @Resource
    private UserInfoDOMapper userInfoDOMapper;

    @Resource
    private UserResourceDOMapper userResourceDOMapper;

    public UserInfoDO selectByLoginName(String loginName){
        return userInfoDOMapper.findByLoginName(loginName);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean insertOrUpdate(UserInfoDO userInfoDO){
        int rows = 0;
        if(userInfoDO.getUserId() == null){
            userInfoDO.setLoginPwd(SHA256.encrypt("123456"));
            userInfoDO.setUserStatus(1);
            userInfoDO.setCreateTime(new Timestamp(System.currentTimeMillis()));
            rows = userInfoDOMapper.insertSelective(userInfoDO);
        }else {
            userInfoDO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            rows = userInfoDOMapper.updateByPrimaryKeySelective(userInfoDO);
        }
        return (rows > 0);
    }

    public PageInfo<UserInfoDO> selectUserList(Long userId, String userName, String page, String size) {
        page = (page==null || StringUtils.isBlank(page))?"1":page;
        size = (size==null || StringUtils.isBlank(size))?"10":size;
        PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(size));
        List<UserInfoDO> list = userInfoDOMapper.selectUserList(userId,userName);
        PageInfo<UserInfoDO> r = new PageInfo<>(list);
        return r;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean resetUserPwd(Long userId) {
        UserInfoDO loginUser = new UserInfoDO();
        loginUser.setUserId(userId);
        loginUser.setLoginPwd(SHA256.encrypt("123456"));
        int rows = userInfoDOMapper.updateByPrimaryKeySelective(loginUser);
        return (rows > 0);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserStatus(Long userId, Integer status) {
        UserInfoDO loginUser = new UserInfoDO();
        loginUser.setUserId(userId);
        loginUser.setUserStatus(status);
        int rows = userInfoDOMapper.updateByPrimaryKeySelective(loginUser);
        return (rows > 0);
    }

    public boolean deleteUser(Long userId) {
        UserInfoDO user = new UserInfoDO();
        user.setUserId(userId);
        user.setUserStatus(4);
        int rows = userInfoDOMapper.updateByPrimaryKeySelective(user);
        return (rows > 0);
    }

    public boolean updateUserPower(Long userId, String json) {
        //将原资源权限设为不可用
        userResourceDOMapper.deleteByUserId(userId);
        StringTokenizer st = new StringTokenizer(json,",");
        while(st.hasMoreTokens()){
            UserResourceDO record = new UserResourceDO();
            record.setUserId(userId);
            record.setResourceId(Long.valueOf(st.nextToken()));
            record.setActive(1);
            record.setCreateTime(new Timestamp(System.currentTimeMillis()));
            userResourceDOMapper.insertSelective(record);
        }
        return true;
    }

    public BaseResult modifyPwd(Long userId, String oldPwd, String newPwd) {
        BaseResult result = new BaseResult();
        result.setResult(false);
        UserInfoDO user = userInfoDOMapper.selectByPrimaryKey(userId);
        if(!user.getLoginPwd().equals(SHA256.encrypt(oldPwd))){
            result.setMessage("原密码不正确");
        }else{
            user.setLoginPwd(SHA256.encrypt(newPwd));
            userInfoDOMapper.updateByPrimaryKeySelective(user);
            result.setResult(true);
        }
        return result;
    }
}
