package com.tang.patent.service;

import com.tang.patent.entity.bean.News;

import java.util.List;
import java.util.Map;

public interface NewsService {

    int countNews();

    boolean addNews(News news);

    List<News> getNewsByPage(Integer page);

    boolean deleNews(Integer id);

    boolean updateNews(News news);

    List<Map<String, Object>> countRecentNews();

    News getNewsById(Integer id);
}
