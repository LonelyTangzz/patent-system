package com.example.patent.service.impl;

import com.example.patent.dao.NewsMapper;
import com.example.patent.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public int countNews() {
        return newsMapper.countNews();
    }
}
