package com.valueservice.djs.db.dao.mini;

import com.valueservice.djs.db.entity.mini.MiniCorps;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MiniCorpsMapper {
    int deleteByPrimaryKey(Long corpsId);

    int insert(MiniCorps record);

    int insertSelective(MiniCorps record);

    MiniCorps selectByPrimaryKey(Long corpsId);

    int updateByPrimaryKeySelective(MiniCorps record);

    int updateByPrimaryKey(MiniCorps record);

    List<MiniCorps> selectCorpsPanking();

    List<MiniCorps> selectSpecialPanking();

    List<MiniCorps> selectCorpsList(@Param("corpsName")String corpsName);

    MiniCorps selectByCorpsName(@Param("corpsName")String corpsName);

    int corpsTop(@Param("corpsId")Long corpsId);

    void corpsCancelTop();

    List<MiniCorps> selectAllCorpsList();

    MiniCorps selectTopCorps();

    MiniCorps selectTop1Corps();
}