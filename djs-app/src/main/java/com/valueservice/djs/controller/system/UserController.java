package com.valueservice.djs.controller.system;

import com.valueservice.djs.bean.BaseResult;
import com.github.pagehelper.PageInfo;
import com.valueservice.djs.controller.BaseController;
import com.valueservice.djs.db.entity.system.ResourcesDO;
import com.valueservice.djs.db.entity.system.UserInfoDO;
import com.valueservice.djs.service.system.ResourceService;
import com.valueservice.djs.service.system.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.valueservice.djs.util.DateUtil;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理
 * @author xiaxin
 * @date 2017-10-11
 */
@Controller
@RequestMapping("/system/user")
public class UserController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    UserInfoService userInfoService;

    @Resource
    ResourceService resourcesService;
    /**
     * 用户列表
     */
    @RequestMapping("/list")
    public String list(ModelMap modelMap, String userName, String pageIndex){
        PageInfo<UserInfoDO> page = null;
        try{
            page = userInfoService.selectUserList(getCurrentUser().getUserId(),userName,pageIndex,null);
        }catch(Exception e){
            logger.error("获取用户列表异常",e);
        }
        modelMap.addAttribute("page",page);
        modelMap.addAttribute("userName",userName);
        return "system/user/list";
    }
    /**
     * 更新用户信息
     */
    @RequestMapping("/updateUser")
    @ResponseBody
    public Map<String,Object> updateUser(UserInfoDO user,String loginInvalidDate){
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("result",false);
        try {
            UserInfoDO record = userInfoService.selectByLoginName(user.getLoginName());
            if(record != null && !record.getUserId().equals(user.getUserId())){
                resultMap.put("msg","抱歉，登录名已存在");
                return resultMap;
            }
            user.setParentId(getCurrentUser().getUserId());
            user.setLoginInvallid(StringUtils.isNotBlank(loginInvalidDate)? DateUtil.StrToTimestamp(loginInvalidDate+" 23:59:59","yyyy-MM-dd HH:mm:ss"):null);
            boolean result = userInfoService.insertOrUpdate(user);
            resultMap.put("result",result);
        }catch(Exception e){
            logger.error("更新用户信息异常",e);
        }
        return resultMap;
    }
    /**
     * 获取用户信息
     */
    @RequestMapping("/getUserInfo")
    @ResponseBody
    public UserInfoDO getUserInfo(String loginName){
        UserInfoDO record = null;
        try{
            record = userInfoService.selectByLoginName(loginName);
        }catch(Exception e){
            logger.error("获取用户信息异常",e);
        }
        return record;
    }
    /**
     * 重置用户密码
     */
    @RequestMapping("/resetUserPwd")
    @ResponseBody
    public Boolean resetUserPwd(Long userId){
        Boolean result = false;
        try {
            result = userInfoService.resetUserPwd(userId);
        }catch(Exception e){
            logger.error("重置用户密码异常",e);
        }
        return result;
    }
    /**
     * 更新用户状态
     */
    @RequestMapping("/updateUserStatus")
    @ResponseBody
    public Boolean updateUserStatus(Long userId,Integer status){
        Boolean result = false;
        try {
            result = userInfoService.updateUserStatus(userId,status);
        }catch(Exception e){
            logger.error("更新用户状态异常",e);
        }
        return result;
    }
    /**
     * 删除用户
     */
    @RequestMapping("/deleteUser")
    @ResponseBody
    public Boolean deleteUser(Long userId){
        Boolean result = false;
        try {
            result = userInfoService.deleteUser(userId);
        }catch(Exception e){
            logger.error("删除用户异常",e);
        }
        return result;
    }
    /**
     * 获取用户的权限列表
     */
    @RequestMapping("/getUserPowerList")
    public @ResponseBody
    List<Map<String,Object>> getCurrUserPowerList(Long userId){
        try {
            List<ResourcesDO> list = null;
            if(userId == null){
                list = resourcesService.selectByUserId(getCurrentUser().getUserId());
            }else{
                list = resourcesService.selectByUserId(userId);
            }
            List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
            //转换为json格式数据，方便zTree树的实现
            for (ResourcesDO t : list) {
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("id", t.getResourceId());
                map.put("pId", t.getParentId());
                map.put("name", t.getResourceName());
                if(t.getParentId() == 0){
                    map.put("open", "true");
                }
                mapList.add(map);
            }
            return mapList;
        } catch (Exception e) {
            logger.error("获取用户的权限列表异常",e);
            return null;
        }
    }
    /**
     * 修改用户权限
     */
    @RequestMapping("/updateUserPower")
    @ResponseBody
    public Boolean updateUserPower(Long userId,String json){
        Boolean result = false;
        try {
            result = userInfoService.updateUserPower(userId,json);
        }catch(Exception e){
            logger.error("修改用户权限异常",e);
        }
        return result;
    }
    /**
     * 修改用户密码
     */
    @RequestMapping("/modifyPwd")
    @ResponseBody
    public BaseResult modifyPwd(String oldPwd, String newPwd){
        BaseResult result = null;
        try{
            result = userInfoService.modifyPwd(getCurrentUser().getUserId(),oldPwd,newPwd);
        }catch (Exception e){
            logger.error("修改用户密码异常",e);
        }
        return result;
    }
}
