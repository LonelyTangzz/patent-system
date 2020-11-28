package com.tang.patent.controller;

import com.alibaba.fastjson.JSONObject;
import com.tang.patent.bean.News;
import com.tang.patent.service.NewsService;
import com.tang.patent.service.PatentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class NewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private PatentService patentService;

    JSONObject jsonObject = new JSONObject();

    /**
     * 添加新闻
     * ----测试通过
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
     * 新闻显示页面
     * ----测试通过
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
     * 删除新闻
     * ----测试通过
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
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "出现异常！");
        }
        return jsonObject;
    }

    /**
     * 修改新闻
     * ---测试通过
     *
     * @return
     */
    @RequestMapping(value = "updateNews.action", method = RequestMethod.POST)
    public Object updateNews(News news) {
        news.setUptime(new Date());
        boolean res = newsService.updateNews(news);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功！");
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改出现异常！");
        }
        return jsonObject;
    }

    /**
     * 显示近七日新闻数量
     * ----测试通过
     *
     * @return
     */
    @RequestMapping(value = "countRecentNews.action", method = RequestMethod.GET)
    public Object countRecentNews() {
        jsonObject.put("result", newsService.countRecentNews());
        return jsonObject;
    }

    /**
     * 单例专利详情
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "newsDetails", method = RequestMethod.GET)
    public ModelAndView newsDetails(HttpServletRequest req) {
        ModelAndView mv = new ModelAndView("user/newsDetails.html");
        mv.addObject("news", newsService.getNewsById(Integer.parseInt(req.getParameter("id"))));
        mv.addObject("allNews", newsService.getNewsByPage(0));
        mv.addObject("totalNews", newsService.countNews());
        mv.addObject("totalPatents", patentService.countPatent());
        return mv;
    }

    /**
     * 用户新闻功能页面
     * ----未测试
     *
     * @return
     */
    @RequestMapping(value = "news")
    public ModelAndView news(HttpServletRequest req) {
        Integer page;
        if (req.getParameter("page") != null)
            page = Integer.parseInt(req.getParameter("page"));
        else
            page = 0;
        ModelAndView mv = new ModelAndView("user/news.html");
        mv.addObject("totalNews", newsService.countNews());
        mv.addObject("totalPatents", patentService.countPatent());
        mv.addObject("news", newsService.getNewsByPage(page));
        return mv;
    }
}
