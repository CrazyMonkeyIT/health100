package com.valueservice.djs.service.mini;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.valueservice.djs.db.dao.mini.MiniCorpsMapper;
import com.valueservice.djs.db.dao.mini.MiniUserDOMapper;
import com.valueservice.djs.db.entity.mini.MiniCorps;
import com.valueservice.djs.db.entity.mini.MiniUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by weiying on 2018/4/8.
 */
@Service
public class MiniCorpService {
    @Resource
    private MiniCorpsMapper miniCorpsMapper;

    @Resource
    private MiniUserDOMapper miniUserDOMapper;

    /**
     * 获取所有的战队信息
     * @return
     */
    public List<MiniCorps> selectAllCorpsList(){return miniCorpsMapper.selectAllCorpsList();}

    /**
     * 获取用户的战队信息
     * @param corpsId
     * @return
     */
    public MiniCorps getCorps(Long corpsId){
        return miniCorpsMapper.selectByPrimaryKey(corpsId);
    }

    /**
     * 获取战队排行  20个
     */
    public List<MiniCorps> selectCorpsPanking(Integer userId){
        List<MiniCorps> reults = new ArrayList<>();
        //处理特殊战队的排行  如果用户属于特殊战队  战队排行内比

        if(Objects.isNull(userId)){
            reults = miniCorpsMapper.selectCorpsPanking();
        }else{
            MiniUser miniUser = miniUserDOMapper.selectByPrimaryKey(userId);
            if(miniUser.getCorpsId()==null){
                reults = miniCorpsMapper.selectCorpsPanking();
            }else{
                MiniCorps miniCorps = miniCorpsMapper.selectByPrimaryKey(miniUser.getCorpsId());
                if(miniCorps!=null && miniCorps.getIsSpecial()==1){
                    reults = miniCorpsMapper.selectSpecialPanking();
                }else{
                    reults = miniCorpsMapper.selectCorpsPanking();
                }
            }
        }
        return reults;
    }

    /**
     * 根据战队名查询战队信息
     * @param corpsName
     * @param page
     * @param size
     * @return
     */
    public PageInfo<MiniCorps> selectCorpsList(String corpsName,String page,String size){
        page = (page==null || StringUtils.isBlank(page))?"1":page;
        size = (size==null || StringUtils.isBlank(size))?"10":size;
        PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(size));
        List<MiniCorps> list = miniCorpsMapper.selectCorpsList(corpsName);
        PageInfo<MiniCorps> r = new PageInfo<>(list);
        return r;
    }

    /**
     * 根据战队名查询战队
     * @param corpsName
     * @return
     */
    public MiniCorps selectByCorpsName(String corpsName){
        return miniCorpsMapper.selectByCorpsName(corpsName);
    }

    /**
     * 加入战队
     * 个人积分减半
     * @param corpsId
     * @param userId
     */
    public String joinCorps(Long corpsId,Integer userId){
        MiniUser miniUser = miniUserDOMapper.selectByPrimaryKey(userId);
        if(miniUser.getCorpsId()!=null)
            return "您已加入过战队";
        miniUser.setCorpsId(corpsId);
        Long point = miniUser.getPoint()==0?0:miniUser.getPoint()/2;
        miniUser.setPoint(point);
        miniUserDOMapper.updateByPrimaryKeySelective(miniUser);
        return "success";
    }

    public boolean saveMiniCorps(MiniCorps miniCorps){
        int result = miniCorpsMapper.insertSelective(miniCorps);
        if(result>0)
            return true;
        return false;
    }

    public boolean updateMiniCorps(MiniCorps miniCorps){
        int result = miniCorpsMapper.updateByPrimaryKeySelective(miniCorps);
        if(result>0)
            return true;
        return false;
    }

    public Boolean deleteCorps(Long corpsId){
        int result = miniCorpsMapper.deleteByPrimaryKey(corpsId);
        if(result>0)
            return true;
        return false;
    }

    public Boolean corpsTop(String corpsId){
        miniCorpsMapper.corpsCancelTop();
        int result = miniCorpsMapper.corpsTop(Long.valueOf(corpsId));
        if(result>0)
            return true;
        return false;
    }

    public void corpsCancelTop(){
        miniCorpsMapper.corpsCancelTop();
    }

    public MiniCorps selectTop1Corps(){
        MiniCorps miniCorps = miniCorpsMapper.selectTopCorps();
        if(Objects.isNull(miniCorps)){
            miniCorps = miniCorpsMapper.selectTop1Corps();
        }
        return miniCorps;
    }

}
