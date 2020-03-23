package com.example.patent.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.patent.bean.Category;
import com.example.patent.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private PatentService patentService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private Category category;

    /**
     * 管理员登录
     * -------测试通过
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "admin.action", method = RequestMethod.POST)
    public Object login(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        boolean res = adminService.veritypasswd(name, password);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "登录成功跳转中");
            jsonObject.put("name", name);
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "用户名或密码错误");
            return jsonObject;
        }
    }

    /**
     * 修改密码
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "changeAdminPwd.action", method = RequestMethod.POST)
    public Object changeAdminPwd(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        String name = req.getParameter("name");
        boolean res = adminService.veritypasswd(name, req.getParameter("usedPwd"));
        if (res) {
            res = adminService.changePasswd(name, req.getParameter("nowPwd"));
            if (res) {
                jsonObject.put("code", 1);
                jsonObject.put("msg", "修改成功！");
                return jsonObject;
            } else {
                jsonObject.put("code",-1);
                jsonObject.put("msg","修改出现异常！");
                return jsonObject;
            }
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "密码错误请重试！");
            return jsonObject;
        }
    }

    /**
     * 添加行业类型
     * --------通过
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "addCategory.action", method = RequestMethod.POST)
    public Object addCategory(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        Category category = new Category();
        if (req.getParameter("typeName") == "") {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加失败请检查是否符合规范");
            return jsonObject;
        }
        category.setCategory(req.getParameter("typeName"));
        boolean res = categoryService.insert(category);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加成功");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "添加失败请检查是否符合规范");
            return jsonObject;
        }
    }

    /**
     * 获取所有行业分类
     * ----------通过
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getAllCategory.action", method = RequestMethod.POST)
    public Object getAllCategory() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("allCategory", categoryService.getAllCategory());
        return jsonObject;
    }

    /**
     * 分页查询行业分类
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getPageCategory.action", method = RequestMethod.POST)
    public Object getPageCategory(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("total", (int) Math.ceil((double) categoryService.countCategory() / 5));
        jsonObject.put("Category", categoryService.getPageCategory((Integer.parseInt(req.getParameter("page")) - 1) * 5));
        return jsonObject;
    }

    /**
     * 删除选择的行业分类
     * --------通过
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteCategory.action", method = RequestMethod.POST)
    public Object getAllCategory(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        boolean res = categoryService.deleteCategory(Integer.parseInt(req.getParameter("id")));
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "删除成功");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "删除出现异常");
            return jsonObject;
        }
    }

    /**
     * 修改分类
     * --------通过
     *
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "editCategory.action", method = RequestMethod.POST)
    public Object editCategory(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
        category.setId(Integer.parseInt(req.getParameter("id")));
        category.setCategory(req.getParameter("name"));
        boolean res = categoryService.updateCategory(category);
        if (res) {
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功！");
            return jsonObject;
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "修改失败，请检查是否符合规范");
            return jsonObject;
        }
    }

    /**
     * 获取所有数据数量
     */
    @ResponseBody
    @RequestMapping(value = "countAll.action", method = RequestMethod.POST)
    public Object countAll() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userCount", userService.countUser());
        jsonObject.put("patentCount", patentService.countPatent());
        jsonObject.put("categoryCount", categoryService.countCategory());
        jsonObject.put("newsCount", newsService.countNews());
        return jsonObject;
    }
}

