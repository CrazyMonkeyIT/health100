package com.valueservice.djs.db.dao.mini;

import com.valueservice.djs.db.entity.mini.PointWasteBook;

public interface PointWasteBookMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PointWasteBook record);

    int insertSelective(PointWasteBook record);

    PointWasteBook selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PointWasteBook record);

    int updateByPrimaryKey(PointWasteBook record);
}