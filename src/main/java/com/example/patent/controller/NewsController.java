package com.example.patent.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.patent.bean.News;
import com.example.patent.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;

    JSONObject jsonObject = new JSONObject();

    /**
     * 添加新闻-----finish
     *
     * @param news
     * @return
     */
    @RequestMapping(value = "addNews.action", method = RequestMethod.POST)
    public Object addNews(News news) {
        news.setUptime(new Date());
        boolean res = newsService.addNews(news);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加成功！");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加失败！");
            return jsonObject;
        }
    }

    /**
     * 新闻显示页面----finish
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "getNewsByPage.action", method = RequestMethod.GET)
    public Object getNewsByPage(Integer page) {
        jsonObject.put("total", (int) Math.ceil((double) newsService.countNews() / 10));
        jsonObject.put("newsList", newsService.getNewsByPage((page - 1) * 10));
        return jsonObject;
    }

    /**
     * 删除新闻-----finish
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "deleNews.action", method = RequestMethod.POST)
    public Object deleNews(Integer id) {
        boolean res = newsService.deleNews(id);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除成功！");
        }else {
            jsonObject.put("code",0);
            jsonObject.put("msg","出现异常！");
        }
        return jsonObject;
    }

    /**
     * 修改新闻---finish
     *
     * @return
     */
    @RequestMapping(value = "updateNews.action",method = RequestMethod.POST)
    public Object updateNews(News news){
        news.setUptime(new Date());
        boolean res = newsService.updateNews(news);
        if(res){
            jsonObject.put("code",1);
            jsonObject.put("msg","修改成功！");
        }else {
            jsonObject.put("code",0);
            jsonObject.put("msg","修改出现异常！");
        }
        return jsonObject;
    }

    /**
     * 显示近七日新闻数量
     *
     * @return
     */
    @RequestMapping(value = "countRecentNews.action",method = RequestMethod.GET)
    public Object countRecentNews(){
        jsonObject.put("result",newsService.countRecentNews());
        return jsonObject;
    }
}
