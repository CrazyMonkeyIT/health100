package com.valueservice.djs.db.dao.system;

import com.valueservice.djs.db.entity.system.UserResourceDO;

public interface UserResourceDOMapper {
    int deleteByPrimaryKey(Long userResourceId);

    int insert(UserResourceDO record);

    int insertSelective(UserResourceDO record);

    UserResourceDO selectByPrimaryKey(Long userResourceId);

    int updateByPrimaryKeySelective(UserResourceDO record);

    int updateByPrimaryKey(UserResourceDO record);

    int deleteByUserId(Long userId);
}