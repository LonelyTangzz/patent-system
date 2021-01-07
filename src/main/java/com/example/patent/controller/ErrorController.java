package com.example.patent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @name: ErrorController
 * @author: tangzy
 * @since: 2021/1/7
 * @version: 1.0
 * @description: 错误页面配置器
 */
@Controller
public class ErrorController {
    private static final String ERROR_PAGE = "page-404.html";

    @RequestMapping("/error400")
    public ModelAndView error400() {
        return new ModelAndView(ERROR_PAGE);
    }

    @RequestMapping("/error401")
    public ModelAndView error401() {
        return new ModelAndView(ERROR_PAGE);
    }

    @RequestMapping("/error404")
    public ModelAndView error404() {
        return new ModelAndView(ERROR_PAGE);
    }

    @RequestMapping("/error500")
    public ModelAndView error500() {
        return new ModelAndView(ERROR_PAGE);
    }
}
