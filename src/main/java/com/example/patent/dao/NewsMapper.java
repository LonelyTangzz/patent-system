package com.example.patent.dao;

import com.example.patent.bean.News;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(News record);

    int insertSelective(News record);

    News selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(News record);

    int updateByPrimaryKey(News record);

    int countNews();
}