package com.valueservice.djs.service.mini;

import com.valueservice.djs.db.bean.MiniUserVO;
import com.valueservice.djs.db.dao.mini.*;
import com.valueservice.djs.db.entity.mini.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class MiniUserService {

    @Resource
    private MiniUserDOMapper miniUserDOMapper;

    @Resource
    private PointWasteBookMapper pointWasteBookMapper;

    @Resource
    private MiniSignMapper miniSignMapper;

    @Resource
    private MiniSignWasteBookMapper miniSignWasteBookMapper;

    @Resource
    private MiniCorpsMapper miniCorpsMapper;

    @Value("${context.path}")
    private String contextPath;

    /**
     * 保存或修改miniUser
     * @param record
     * @return
     * @throws Exception
     */
    public synchronized MiniUser saveOrUpdate(MiniUser record) throws Exception{
        MiniUser existsUser = miniUserDOMapper.selectByOpenId(record.getOpenId());
        if(existsUser == null){
            record.setActive(1);
            record.setCreateTime(new Timestamp(System.currentTimeMillis()));
            miniUserDOMapper.insertSelective(record);
            return record;
        }else {
            record.setId(existsUser.getId());
            record.setUpdateTime(new Timestamp(System.currentTimeMillis()
            ));
            miniUserDOMapper.updateByPrimaryKeySelective(record);
            return existsUser;
        }
    }

    public void sharePoint(Integer userId){
        /*MiniUser miniUser = miniUserDOMapper.selectByPrimaryKey(userId);
        miniUser.setPoint(miniUser.getPoint()+20);
        miniUserDOMapper.updateByPrimaryKeySelective(miniUser);*/
    }

    /**
     * 邀请获得积分
     * @param userId
     * @param scene
     */
    public void sharePointInvoke(Integer userId,Integer scene){
        //被邀请的用户
        MiniUser invitedUser = miniUserDOMapper.selectByPrimaryKey(userId);
        if(invitedUser != null){
            //发出邀请的用户
            MiniUser inviteUser = miniUserDOMapper.selectByPrimaryKey(scene);
            inviteUser.setPoint(inviteUser.getPoint()+20);
            miniUserDOMapper.updateByPrimaryKeySelective(inviteUser);
        }
    }
    /**
     * 根据openId获取 微信用户  并返回 vo对象
     * @param userId
     * @return
     */
    public MiniUserVO getMiniUserByOpenId(Integer userId){
        MiniUser miniUser = miniUserDOMapper.selectByPrimaryKey(userId);
        return resultMiniUser(miniUser);
    }

    /**
     * 格式化返回的user对象
     * @param miniUser
     * @return
     */
    public MiniUserVO resultMiniUser(MiniUser miniUser){
        MiniUserVO miniUserVO = null;
        if(!Objects.isNull(miniUser)){
            miniUserVO = new MiniUserVO();
            miniUserVO.setUserId(miniUser.getId());
            miniUserVO.setPoint(miniUser.getPoint()==null?0L:miniUser.getPoint());
            miniUserVO.setNickName(miniUser.getNickName());
            miniUserVO.setAvatar(miniUser.getAvatarUrl());
            miniUserVO.setCorpsId(miniUser.getCorpsId()==null?0L:miniUser.getCorpsId());
            miniUserVO.setHasCorps(miniUser.getCorpsId()==null?false:miniUser.getCorpsId()==0?false:true);
            miniUserVO.setHasFirstSign(StringUtils.isNotBlank(miniUser.getFirstSignPost())?true:false);

            MiniCorps miniCorps = miniCorpsMapper.selectByPrimaryKey(miniUser.getCorpsId());
            if(!Objects.isNull(miniCorps)){
                miniUserVO.setCorpsName(miniCorps.getCorpsName());
                miniUserVO.setCorpsPoint(miniCorps.getPoint());
            }else{
                miniUserVO.setCorpsName("");
                miniUserVO.setCorpsPoint(0L);
            }

            MiniSign sign = miniSignMapper.selectByMiniUserId(miniUser.getId());
            if(sign!=null){
                miniUserVO.setOneSign(false);
                miniUserVO.setSignDay(sign.getSignDays());
                miniUserVO.setSignCountDay(sign.getCountDays());
                miniUserVO.setAchieveShow(false);
                //判断是否需要弹出成就
                if(sign.getCountDays()==7 && sign.getCountDays7()==0){
                    miniUserVO.setAchieveShow(true);
                    //miniUserVO.setAchieveImage("../images/achieve/sign_countday_7.png");
                    miniUserVO.setAchieveImage(contextPath + "minifile/achieve/sign_countday_7.png");
                    miniSignMapper.updateCountDay7(sign.getSignId());
                }else if(sign.getCountDays()==30 && sign.getCountDays30()==0){
                    miniUserVO.setAchieveShow(true);
                    //miniUserVO.setAchieveImage("../images/achieve/sign_countday_30.png");
                    miniUserVO.setAchieveImage(contextPath + "minifile/achieve/sign_countday_30.png");
                    miniSignMapper.updateCountDay30(sign.getSignId());
                }else if(sign.getCountDays()==60 && sign.getCountDays60()==0){
                    miniUserVO.setAchieveShow(true);
                    //miniUserVO.setAchieveImage("../images/achieve/sign_countday_60.png");
                    miniUserVO.setAchieveImage(contextPath + "minifile/achieve/sign_countday_60.png");
                    miniSignMapper.updateCountDay60(sign.getSignId());
                }else if(sign.getCountDays()==100 && sign.getCountDays100()==0){
                    miniUserVO.setAchieveShow(true);
                    //miniUserVO.setAchieveImage("../images/achieve/sign_countday_100.png");
                    miniUserVO.setAchieveImage(contextPath + "minifile/achieve/sign_countday_100.png");
                    miniSignMapper.updateCountDay100(sign.getSignId());
                }
                //取勋章
                if(sign.getCountDays()<7){
                    miniUserVO.setBadge1("../images/user/badge_1_n.png");
                    miniUserVO.setBadge2("../images/user/badge_2_n.png");
                    miniUserVO.setBadge3("../images/user/badge_3_n.png");
                    miniUserVO.setBadge4("../images/user/badge_4_n.png");
                }else if(sign.getCountDays()>=7 && sign.getCountDays()<30){
                    miniUserVO.setBadge1("../images/user/badge_1_y.png");
                    miniUserVO.setBadge2("../images/user/badge_2_n.png");
                    miniUserVO.setBadge3("../images/user/badge_3_n.png");
                    miniUserVO.setBadge4("../images/user/badge_4_n.png");
                }else if(sign.getCountDays()>=30 && sign.getCountDays()<60){
                    miniUserVO.setBadge1("../images/user/badge_1_y.png");
                    miniUserVO.setBadge2("../images/user/badge_2_y.png");
                    miniUserVO.setBadge3("../images/user/badge_3_n.png");
                    miniUserVO.setBadge4("../images/user/badge_4_n.png");
                }else if(sign.getCountDays()>=60 && sign.getCountDays()<100){
                    miniUserVO.setBadge1("../images/user/badge_1_y.png");
                    miniUserVO.setBadge2("../images/user/badge_2_y.png");
                    miniUserVO.setBadge3("../images/user/badge_3_y.png");
                    miniUserVO.setBadge4("../images/user/badge_4_n.png");
                }else if(sign.getCountDays()==100){
                    miniUserVO.setBadge1("../images/user/badge_1_y.png");
                    miniUserVO.setBadge2("../images/user/badge_2_y.png");
                    miniUserVO.setBadge3("../images/user/badge_3_y.png");
                    miniUserVO.setBadge4("../images/user/badge_4_y.png");
                }
            }else{
                miniUserVO.setBadge1("../images/user/badge_1_n.png");
                miniUserVO.setBadge2("../images/user/badge_2_n.png");
                miniUserVO.setBadge3("../images/user/badge_3_n.png");
                miniUserVO.setBadge4("../images/user/badge_4_n.png");
                miniUserVO.setSignDay(0);
                miniUserVO.setSignCountDay(0);
                miniUserVO.setOneSign(true);
            }
        }
        return miniUserVO;
    }

    public String getMiniUserCountDay(Integer userId){
        MiniSign miniSign = miniSignMapper.selectByMiniUserId(userId);
        if(!Objects.isNull(miniSign)){

        }
        return "";
    }
    /**
     * 获取用户积分排行榜 前30
     * @return
     */
    public List<MiniUser> selectUserPanking(){
        return miniUserDOMapper.selectUserPanking();
    }

    public List<MiniUser> selectCorpsUserPanking(Integer userId){
        MiniUser miniUser = miniUserDOMapper.selectByPrimaryKey(userId);
        return miniUserDOMapper.selectCorpsUserPanking(miniUser.getCorpsId());
    }
    /**
     * 通过openId查询用户信息
     * @param openId
     * @return
     */
    public MiniUser selectByOpenId(String openId){
        return miniUserDOMapper.selectByOpenId(openId);
    }

    public MiniUser selectByPrimaryKey(Integer userId){return miniUserDOMapper.selectByPrimaryKey(userId);};

    public List<MiniSignWasteBook> selectUserSignImage(Integer userId){
        return miniSignWasteBookMapper.selectUserSignImage(userId);
    }

    public MiniUserVO getMiniUserAchieve(Integer userId){
        MiniUserVO miniUserVO = new MiniUserVO();
        return miniUserVO;
    }

    public void savePointWastBook(Long point,String pointSource,String openId){
        PointWasteBook pointWasteBook = new PointWasteBook();
        pointWasteBook.setPoint(point);
        pointWasteBook.setPointSource(pointSource);
        pointWasteBook.setOpenId(openId);
        pointWasteBook.setCreateTime(new Date());
        pointWasteBookMapper.insert(pointWasteBook);
    }

}
