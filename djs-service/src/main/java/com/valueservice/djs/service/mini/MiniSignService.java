package com.valueservice.djs.service.mini;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.valueservice.djs.db.dao.mini.MiniSignMapper;
import com.valueservice.djs.db.dao.mini.MiniSignWasteBookMapper;
import com.valueservice.djs.db.dao.mini.MiniUserDOMapper;
import com.valueservice.djs.db.entity.mini.MiniSign;
import com.valueservice.djs.db.entity.mini.MiniSignWasteBook;
import com.valueservice.djs.db.entity.mini.MiniUserDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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

    public PageInfo<MiniSignWasteBook> selectNotCheckWasteBook(String page,String size){
        page = (page==null || StringUtils.isBlank(page))?"1":page;
        size = (size==null || StringUtils.isBlank(size))?"10":size;
        PageHelper.startPage(Integer.parseInt(page), Integer.parseInt(size));
        List<MiniSignWasteBook> list = miniSignWasteBookMapper.selectNotCheckWasteBook();
        PageInfo<MiniSignWasteBook> r = new PageInfo<>(list);
        return r;
    }

    public Boolean checkImage(String id){
        Boolean result = true;
        MiniSignWasteBook miniSignWasteBook = miniSignWasteBookMapper.selectByPrimaryKey(Long.valueOf(id));
        if(miniSignWasteBook == null)
            return false;

        MiniSign miniSign = miniSignMapper.selectByPrimaryKey(miniSignWasteBook.getSignId());
        if(miniSign == null)
            return false;

        MiniUserDO miniUser = miniUserDOMapper.selectByPrimaryKey(miniSign.getMiniUserId().intValue());
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
}
