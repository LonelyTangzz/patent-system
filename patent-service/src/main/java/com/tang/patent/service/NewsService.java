package com.tang.patent.service;

import com.tang.patent.entity.bean.News;
import com.tang.vos.news.NewsVo;

import java.util.List;
import java.util.Map;

public interface NewsService {

    int countNews();

    boolean addNews(News news);

    /**
     * 分页获取新闻信息
     *
     * @param page 页码
     * @return 新闻信息
     */
    List<NewsVo> getNewsByPage(Integer page);

    boolean deleNews(Integer id);

    boolean updateNews(News news);

    List<Map<String, Object>> countRecentNews();

    News getNewsById(Integer id);
}
