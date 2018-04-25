package com.valueservice.djs.service.mini;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.valueservice.djs.db.dao.mini.MiniSignMapper;
import com.valueservice.djs.db.dao.mini.MiniSignWasteBookMapper;
import com.valueservice.djs.db.dao.mini.MiniUserDOMapper;
import com.valueservice.djs.db.entity.mini.MiniSign;
import com.valueservice.djs.db.entity.mini.MiniSignWasteBook;
import com.valueservice.djs.db.entity.mini.MiniUser;
import com.valueservice.djs.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by weiying on 2018/4/13.
 */
@Service
public class MiniSignService {
    @Resource
    private MiniSignWasteBookMapper miniSignWasteBookMapper;

    @Resource
    private MiniSignMapper miniSignMapper;

    @Resource
    private MiniUserDOMapper miniUserDOMapper;

    /**
     * 获取咩有确认的传图打卡记录
     * @param page
     * @param size
     * @return
     */
    public PageInfo<MiniSignWasteBook> selectNotCheckWasteBook(String page,String size){
        page = (page==null || StringUtils.isBlank(page))?"1":page;
        size = (size==null || StringUtils.isBlank(size))?"10":size;
        PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(size));
        List<MiniSignWasteBook> list = miniSignWasteBookMapper.selectNotCheckWasteBook();
        PageInfo<MiniSignWasteBook> r = new PageInfo<>(list);
        return r;
    }

    /**
     * 传图打卡确认
     * @param id
     * @return
     */
    public Boolean checkImage(String id){
        Boolean result = true;
        MiniSignWasteBook miniSignWasteBook = miniSignWasteBookMapper.selectByPrimaryKey(Long.valueOf(id));
        if(miniSignWasteBook == null)
            return false;

        MiniSign miniSign = miniSignMapper.selectByPrimaryKey(miniSignWasteBook.getSignId());
        if(miniSign == null)
            return false;

        MiniUser miniUser = miniUserDOMapper.selectByPrimaryKey(miniSign.getMiniUserId().intValue());
        if(miniUser == null)
            return false;

        Long point = miniUser.getPoint()+20*miniSign.getCountDays();
        miniUser.setPoint(point);
        miniUserDOMapper.updateByPrimaryKeySelective(miniUser);

        miniSignWasteBook.setIsCheck(1);
        miniSignWasteBook.setCheckTime(new Timestamp(new Date().getTime()));
        miniSignWasteBookMapper.updateByPrimaryKeySelective(miniSignWasteBook);

        return result;
    }

    /**
     * 获取用户打卡状态
     * @param userId
     * @return
     */
    public MiniSign checkSign(Integer userId){
        return miniSignMapper.selectByMiniUserId(userId);
    }

    /**
     * 传图用户打卡
     * @param userId
     * @param imagePath
     * @return
     */
    public String imageSign(Integer userId,String imagePath){
        return saveOrUpdateSign(userId,imagePath);
    }

    public String userSign(Integer userId)throws Exception{
        String result = saveOrUpdateSign(userId,"");
        if(result.equals("success")){
            MiniUser miniUser = miniUserDOMapper.selectByPrimaryKey(userId);
            MiniSign miniSign = miniSignMapper.selectByMiniUserId(userId);
            miniUser.setPoint(miniUser.getPoint()+5*miniSign.getCountDays());
            miniUser.setUpdateTime(new Timestamp(new Date().getTime()));
            miniUserDOMapper.updateByPrimaryKeySelective(miniUser);
        }
        return result;
    }
    /**
     * 创建打卡记录
     * @param userId
     * @param imagePath
     * @return
     */
    public String saveOrUpdateSign(Integer userId,String imagePath){
        try{
            MiniSign miniSign = miniSignMapper.selectByMiniUserId(userId);
            if(Objects.isNull(miniSign)){ //第一次打卡
                miniSign = new MiniSign();
                miniSign.setMiniUserId(Long.valueOf(userId));
                miniSign.setLastSignTime(new Timestamp(new Date().getTime()));
                miniSign.setCountDays(1);
                miniSign.setSignDays(1);
                miniSignMapper.insertSelective(miniSign);
            }else{
                /**当天是否已经打过卡**/
                if(DateUtil.daysBetween(miniSign.getLastSignTime(),new Date())==0){
                    return "Signed in today";
                }else if (DateUtil.daysBetween(miniSign.getLastSignTime(),new Date())==1){
                    /**连续打卡**/
                    MiniSignWasteBook miniSignWasteBook = miniSignWasteBookMapper.selectLastSignWasteBook(miniSign.getSignId());
                    /**传图打卡  并且没有确认  连续打卡作废**/
                    if(miniSignWasteBook.getSignType()==1 && miniSignWasteBook.getIsCheck()==0){
                        miniSign.setCountDays(1);
                    }else{
                        /**已确认的传图打卡  和  一键打卡**/
                        miniSign.setCountDays(miniSign.getCountDays()+1);
                    }
                }else{
                    /**非连续打卡**/
                    miniSign.setCountDays(1);
                }
                miniSign.setSignDays(miniSign.getSignDays()+1);
                miniSign.setLastSignTime(new Timestamp(new Date().getTime()));
                miniSignMapper.updateByPrimaryKeySelective(miniSign);
            }
            saveSignWasteBook(miniSign.getSignId(),imagePath);
        }catch (Exception e){
            e.printStackTrace();
            return "error";
        }
        return "success";
    }

    /**
     * 创建打卡流水
     * @param signId
     * @param imagePath
     */
    public void saveSignWasteBook(Long signId,String imagePath){
        MiniSignWasteBook miniSignWasteBook = new MiniSignWasteBook();
        miniSignWasteBook.setSignId(signId);
        miniSignWasteBook.setSignTime(new Timestamp(new Date().getTime()));
        /**传图打卡**/
        if(StringUtil.isNotEmpty(imagePath)){
            miniSignWasteBook.setSignType(1);
            miniSignWasteBook.setImagePath(imagePath);
        }else{
            miniSignWasteBook.setSignType(0);
        }
        miniSignWasteBookMapper.insertSelective(miniSignWasteBook);
    }

}
