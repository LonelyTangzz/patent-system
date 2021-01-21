package com.tang.patent.dao;

import com.tang.patent.entity.bean.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface NewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);

    int countNews();

    List<News> getNewsByPage(@Param("page") Integer page);

    List<Map<String, Object>> countRecentNews();
}