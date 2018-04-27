package com.valueservice.djs.db.dao.mini;

import com.valueservice.djs.db.entity.mini.MiniUser;

import java.util.List;

public interface MiniUserDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MiniUser record);

    int insertSelective(MiniUser record);

    MiniUser selectByPrimaryKey(Integer id);

    Integer selectRank(Integer id);

    int updateByPrimaryKeySelective(MiniUser record);

    int updateByPrimaryKey(MiniUser record);

    MiniUser selectByOpenId(String openId);

    List<MiniUser> selectUserPanking();
}