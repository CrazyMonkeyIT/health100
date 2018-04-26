package com.valueservice.djs.service.mini;

import com.valueservice.djs.db.bean.MiniUserVO;
import com.valueservice.djs.db.dao.mini.MiniCorpsMapper;
import com.valueservice.djs.db.dao.mini.MiniSignMapper;
import com.valueservice.djs.db.dao.mini.MiniUserDOMapper;
import com.valueservice.djs.db.dao.mini.PointWasteBookMapper;
import com.valueservice.djs.db.entity.mini.MiniCorps;
import com.valueservice.djs.db.entity.mini.MiniSign;
import com.valueservice.djs.db.entity.mini.MiniUser;
import com.valueservice.djs.db.entity.mini.PointWasteBook;
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
    private MiniCorpsMapper miniCorpsMapper;

    /**
     * 保存或修改miniUser
     * @param record
     * @return
     * @throws Exception
     */
    public MiniUser saveOrUpdate(MiniUser record) throws Exception{
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
                if(sign.getSignDays()>=1 && sign.getSignDays()<30){
                    miniUserVO.setBadge1("../images/user/badge_1_y.png");
                    miniUserVO.setBadge2("../images/user/badge_2_n.png");
                    miniUserVO.setBadge3("../images/user/badge_3_n.png");
                    miniUserVO.setBadge4("../images/user/badge_4_n.png");
                }else if(sign.getSignDays()>=30 && sign.getSignDays()<60){
                    miniUserVO.setBadge1("../images/user/badge_1_y.png");
                    miniUserVO.setBadge2("../images/user/badge_2_y.png");
                    miniUserVO.setBadge3("../images/user/badge_3_n.png");
                    miniUserVO.setBadge4("../images/user/badge_4_n.png");
                }else if(sign.getSignDays()>=60 && sign.getSignDays()<100){
                    miniUserVO.setBadge1("../images/user/badge_1_y.png");
                    miniUserVO.setBadge2("../images/user/badge_2_y.png");
                    miniUserVO.setBadge3("../images/user/badge_3_y.png");
                    miniUserVO.setBadge4("../images/user/badge_4_n.png");
                }else if(sign.getSignDays()==100){
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
    /**
     * 获取用户积分排行榜 前30
     * @return
     */
    public List<MiniUser> selectUserPanking(){
        return miniUserDOMapper.selectUserPanking();
    }
    /**
     * 通过openId查询用户信息
     * @param openId
     * @return
     */
    public MiniUser selectByOpenId(String openId){
        return miniUserDOMapper.selectByOpenId(openId);
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
