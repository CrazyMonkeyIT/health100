package com.valueservice.djs.db.dao.mini;

import com.valueservice.djs.db.entity.mini.MiniUserDO;

import java.util.List;

public interface MiniUserDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MiniUserDO record);

    int insertSelective(MiniUserDO record);

    MiniUserDO selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MiniUserDO record);

    int updateByPrimaryKey(MiniUserDO record);

    MiniUserDO selectByOpenId(String openId);

    List<MiniUserDO> selectUserPanking();
}