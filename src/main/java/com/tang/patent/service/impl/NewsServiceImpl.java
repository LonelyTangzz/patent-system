package com.tang.patent.service.impl;

import com.tang.patent.bean.News;
import com.tang.patent.dao.NewsMapper;
import com.tang.patent.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public int countNews() {
        return newsMapper.countNews();
    }

    @Override
    public boolean addNews(News news) {
        return newsMapper.insertSelective(news) > 0 ? true : false;
    }

    @Override
    public List<News> getNewsByPage(Integer page) {
        return newsMapper.getNewsByPage(page);
    }

    @Override
    public boolean deleNews(Integer id) {
        return newsMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

    @Override
    public boolean updateNews(News news) {
        return newsMapper.updateByPrimaryKeySelective(news) > 0 ? true : false;
    }

    @Override
    public List<Map<String, Object>> countRecentNews() {
        return newsMapper.countRecentNews();
    }

    @Override
    public News getNewsById(Integer id) {
        return newsMapper.selectByPrimaryKey(id);
    }
}
