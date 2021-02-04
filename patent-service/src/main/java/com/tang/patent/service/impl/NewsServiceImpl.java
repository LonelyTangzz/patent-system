package com.tang.patent.service.impl;

import com.tang.patent.entity.bean.News;
import com.tang.patent.dao.NewsMapper;
import com.tang.patent.service.NewsService;
import com.tang.vos.news.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author tangzy
 * @version 1.0
 * @name NewsServiceImpl
 * @description: 新闻业务服务层
 * @since 2021/1/9
 */
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

    /**
     * 分页获取新闻信息
     *
     * @param page 页码
     * @return 新闻信息
     */
    @Override
    public List<NewsVo> getNewsByPage(Integer page) {
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
        return newsMapper.selectByPrimaryKey((long) id);
    }
}
