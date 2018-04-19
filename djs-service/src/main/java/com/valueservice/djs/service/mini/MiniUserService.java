package com.valueservice.djs.service.mini;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.valueservice.djs.db.dao.mini.MiniSignMapper;
import com.valueservice.djs.db.dao.mini.MiniUserDOMapper;
import com.valueservice.djs.db.dao.mini.PointWasteBookMapper;
import com.valueservice.djs.db.entity.mini.MiniSign;
import com.valueservice.djs.db.entity.mini.MiniUserDO;
import com.valueservice.djs.db.entity.mini.PointWasteBook;
import com.valueservice.djs.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class MiniUserService {

    @Resource
    private MiniUserDOMapper miniUserDOMapper;

    @Resource
    private PointWasteBookMapper pointWasteBookMapper;

    @Resource
    private MiniSignMapper miniSignMapper;

    /**
     * 保存或修改miniUser
     * @param record
     * @return
     * @throws Exception
     */
    public MiniUserDO saveOrUpdate(MiniUserDO record) throws Exception{
        MiniUserDO existsUser = miniUserDOMapper.selectByOpenId(record.getOpenId());
        if(existsUser == null){
            record.setActive(1);
            record.setCreateTime(new Timestamp(System.currentTimeMillis()));
            miniUserDOMapper.insert(record);
            return resultMiniUser(record);
        }else {
            record.setId(existsUser.getId());
            record.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            miniUserDOMapper.updateByPrimaryKeySelective(record);
            return resultMiniUser(existsUser);
        }
    }

    /**
     * 格式化返回的user对象
     * @param miniUser
     * @return
     */
    public MiniUserDO resultMiniUser(MiniUserDO miniUser){
        MiniSign sign = miniSignMapper.selectByMiniUserId(Long.valueOf(miniUser.getId()));
        if(sign!=null)
            miniUser.setOneSign(false);
        miniUser.setOneSign(true);
        return miniUser;
    }
    /**
     * 获取用户积分排行榜 前30
     * @return
     */
    public List<MiniUserDO> selectUserPanking(){
        return miniUserDOMapper.selectUserPanking();
    }
    /**
     * 通过openId查询用户信息
     * @param openId
     * @return
     */
    public MiniUserDO selectByOpenId(String openId){
        return miniUserDOMapper.selectByOpenId(openId);
    }

    /**
     * 加入战队 个人积分减半
     * @param openId
     * @param corpsId
     * @return
     */
    public Integer initCorps(String openId, Long corpsId){
        MiniUserDO miniUser = miniUserDOMapper.selectByOpenId(openId);
        Long point = miniUser.getPoint()==0?0:miniUser.getPoint()/2;
        miniUser.setPoint(point);
        miniUser.setCorpsId(corpsId);
        savePointWastBook(0-point,"3",openId);
        return miniUserDOMapper.updateByPrimaryKey(miniUser);
    }

    /**
     * 打卡  一键打卡
     * @param openId
     * @return
     */
//    public MiniUserDO puchClock(String openId){
//        MiniUserDO miniUserDO = miniUserDOMapper.selectByOpenId(openId);
//        if(miniUserDO!=null){
//            Long point = getPoint(miniUserDO.getId().longValue(),5L,"");
//            miniUserDO.setPoint(point);
//            miniUserDOMapper.updateByPrimaryKey(miniUserDO);
//            savePointWastBook(point,"1",openId);
//        }
//        return miniUserDO;
//    }

    /**
     * 计算用户本次打卡获取积分
     * 保存打卡记录
     * @param userId
     * @param initPoint  积分计算的基数  5：一键打卡 20：传图打卡
     * @return
     */
//    private Long getPoint(Long userId,Long initPoint,String imagePath){
//        Long point = 0L;
//        PunchClockWasteBook wasteBook = punchClockWasteBookMapper.selectByUserId(userId);
//        if(wasteBook.getPunchClockTime()!=null && DateUtil.daysBetween(new Date(),wasteBook.getPunchClockTime())>1){
//            //非连续打卡  初始化打卡记录信息
//            wasteBook.setMiniUserId(userId);
//            wasteBook.setPunchClockTime(new Date());
//            wasteBook.setLastPunchClockTime(new Date());
//            wasteBook.setContinuousDays(1);
//            if(initPoint == 20){
//                //上传图片打卡  需要审核 先记录信息 审核通过直接 用连续天数计算积分
//                wasteBook.setIsCheck(0);
//                wasteBook.setImagePath(imagePath);
//            }else{
//                //一键打卡
//                wasteBook.setIsCheck(1);
//            }
//            punchClockWasteBookMapper.insert(wasteBook);
//        }else{
//            //连续打卡
//            wasteBook.setLastPunchClockTime(wasteBook.getPunchClockTime());
//            wasteBook.setPunchClockTime(new Date());
//            wasteBook.setContinuousDays(wasteBook.getContinuousDays()+1);
//            if(initPoint == 20){
//                //上传图片打卡  需要审核 先记录信息 审核通过直接 用连续天数计算积分
//                wasteBook.setIsCheck(0);
//                wasteBook.setImagePath(imagePath);
//            }else{
//                //一键打卡
//                wasteBook.setIsCheck(1);
//            }
//            punchClockWasteBookMapper.updateByPrimaryKey(wasteBook);
//        }
//
//        point = initPoint*(wasteBook.getContinuousDays());
//        return point;
//    }

    /**
     * 传图打卡 审核通过
     * 增加积分
     * @param
     * @return
     */
//    public Boolean checkImage(Long id){
//        PunchClockWasteBook wasteBook = punchClockWasteBookMapper.selectByPrimaryKey(id);
//        if(wasteBook==null)
//            return false;
//
//        MiniUserDO miniUser = miniUserDOMapper.selectByPrimaryKey(wasteBook.getMiniUserId().intValue());
//        if(miniUser == null)
//            return false;
//        Integer point = 20*wasteBook.getContinuousDays();
//        miniUser.setPoint(miniUser.getPoint()+point);
//        int userResult = miniUserDOMapper.updateByPrimaryKeySelective(miniUser);
//        if(userResult==0)
//            return false;
//
//        wasteBook.setIsCheck(1);
//        int result = punchClockWasteBookMapper.updateByPrimaryKeySelective(wasteBook);
//        if(result==0)
//            return false;
//
//        savePointWastBook(Long.valueOf(point),"2",miniUser.getOpenId());
//        return true;
//    }

    public void savePointWastBook(Long point,String pointSource,String openId){
        PointWasteBook pointWasteBook = new PointWasteBook();
        pointWasteBook.setPoint(point);
        pointWasteBook.setPointSource(pointSource);
        pointWasteBook.setOpenId(openId);
        pointWasteBook.setCreateTime(new Date());
        pointWasteBookMapper.insert(pointWasteBook);
    }

}
