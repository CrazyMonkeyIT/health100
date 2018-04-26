package com.valueservice.djs.db.dao.mini;

import com.valueservice.djs.db.entity.mini.MiniSignWasteBook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MiniSignWasteBookMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MiniSignWasteBook record);

    int insertSelective(MiniSignWasteBook record);

    MiniSignWasteBook selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MiniSignWasteBook record);

    int updateByPrimaryKey(MiniSignWasteBook record);

    List<MiniSignWasteBook> selectNotCheckWasteBook();

    MiniSignWasteBook selectLastSignWasteBook(@Param("signId")Long signId);

    List<MiniSignWasteBook> selectUserSignImage(@Param("userId")Integer userId);
}