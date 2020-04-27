package com.example.patent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping("/error400")
    public ModelAndView error400() {
        return new ModelAndView("page-404.html");
    }
    @RequestMapping("/error401")
    public ModelAndView error401() {
        return new ModelAndView("page-404.html");
    }
    @RequestMapping("/error404")
    public ModelAndView error404() {
        return new ModelAndView("page-404.html");
    }
    @RequestMapping("/error500")
    public ModelAndView error500() {
        return new ModelAndView("page-404.html");
    }
}
