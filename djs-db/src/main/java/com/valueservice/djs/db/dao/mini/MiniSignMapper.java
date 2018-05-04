package com.valueservice.djs.db.dao.mini;

import com.valueservice.djs.db.entity.mini.MiniSign;
import org.apache.ibatis.annotations.Param;

public interface MiniSignMapper {
    int deleteByPrimaryKey(Long signId);

    int insert(MiniSign record);

    int insertSelective(MiniSign record);

    MiniSign selectByPrimaryKey(Long signId);

    int updateByPrimaryKeySelective(MiniSign record);

    int updateByPrimaryKey(MiniSign record);

    MiniSign selectByMiniUserId(@Param("miniUserId")Integer miniUserId);

    int updateCountDay7(Long signId);

    int updateCountDay30(Long signId);

    int updateCountDay60(Long signId);

    int updateCountDay100(Long signId);
}