package com.valueservice.djs.db.dao.mini;

import com.valueservice.djs.db.entity.mini.PostPicItem;

import java.util.List;

public interface PostPicItemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PostPicItem record);

    int insertSelective(PostPicItem record);

    PostPicItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PostPicItem record);

    int updateByPrimaryKey(PostPicItem record);

    List<PostPicItem> selectByType(String type);
}