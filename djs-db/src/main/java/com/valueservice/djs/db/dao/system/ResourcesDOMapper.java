package com.valueservice.djs.db.dao.system;

import com.valueservice.djs.db.entity.system.ResourcesDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResourcesDOMapper {
    int deleteByPrimaryKey(Long resourceId);

    int insert(ResourcesDO record);

    int insertSelective(ResourcesDO record);

    ResourcesDO selectByPrimaryKey(Long resourceId);

    List<ResourcesDO> selectByUserId(Long userId);

    List<ResourcesDO> selectAllActive();

    int updateByPrimaryKeySelective(ResourcesDO record);

    int updateByPrimaryKey(ResourcesDO record);

    Integer selectLasrOrderNo(@Param("parentId")Long parentId);
}