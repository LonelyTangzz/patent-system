package com.example.patent.service;

import com.example.patent.bean.News;

import java.util.List;
import java.util.Map;

public interface NewsService {

    int countNews();

    boolean addNews(News news);

    List<News> getNewsByPage(Integer page);

    boolean deleNews(Integer id);

    boolean updateNews(News news);

    List<Map<String, Object>> countRecentNews();
}
