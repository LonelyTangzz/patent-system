package com.example.patent.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/7.
 * @name: PageController
 * @description: 页面配置加载控制器
 */
@RestController
public class PageController {
    /**
     * 以下均为管理员生成相应界面
     */
    @RequestMapping(value = "admin/login")
    public ModelAndView login() {
        return new ModelAndView("admin/login.html");
    }

    @RequestMapping(value = "admin/main")
    public ModelAndView main() {
        return new ModelAndView("admin/main.html");
    }

    @RequestMapping(value = "admin/patentControl")
    public ModelAndView patentControl() {
        return new ModelAndView("admin/patentControl.html");
    }

    @RequestMapping(value = "admin/newsControl")
    public ModelAndView newsControl() {
        return new ModelAndView("admin/newsControl.html");
    }

    @RequestMapping(value = "admin/categoryControl")
    public ModelAndView categoryControl() {
        return new ModelAndView("admin/categoryControl.html");
    }

    @RequestMapping(value = "admin/userControl")
    public ModelAndView userControl() {
        return new ModelAndView("admin/userControl.html");
    }

    @RequestMapping(value = "admin/addNews")
    public ModelAndView addNews() {
        return new ModelAndView("admin/addNews.html");
    }

    @RequestMapping(value = "admin/changePassword")
    public ModelAndView changePassword() {
        return new ModelAndView("admin/changePassword.html");
    }
}
