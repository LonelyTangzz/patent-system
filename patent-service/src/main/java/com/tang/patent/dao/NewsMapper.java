package com.tang.patent.dao;

import com.tang.patent.entity.bean.News;
import com.tang.vos.news.NewsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface NewsMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(News record);

    News selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(News record);

    int countNews();

    /**
     * 分页获取新闻信息
     *
     * @param page 页码
     * @return 新闻信息
     */
    List<NewsVo> getNewsByPage(@Param("page") Integer page);

    List<Map<String, Object>> countRecentNews();
}