package com.valueservice.djs.db.dao.system;

import com.valueservice.djs.db.entity.system.UserInfoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserInfoDOMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserInfoDO record);

    int insertSelective(UserInfoDO record);

    UserInfoDO selectByPrimaryKey(Long userId);

    UserInfoDO findByLoginName(String loginName);

    int updateByPrimaryKeySelective(UserInfoDO record);

    int updateByPrimaryKey(UserInfoDO record);

    List<UserInfoDO> selectUserList(@Param("userId") Long userId, @Param("userName") String userName);
}