package com.example.patent.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.patent.entity.basic.BaseResp;
import com.example.patent.entity.basic.ResponseResult;
import com.example.patent.entity.basic.ResultType;
import com.example.patent.entity.bean.Category;
import com.example.patent.entity.param.PasswordChangeParams;
import com.example.patent.service.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @name: AdminController
 * @author: tangzy
 * @since: 2021/1/7
 * @version: 1.0
 * @description: 管理员控制层
 */
@RestController
@RequestMapping("admin")
public class AdminController extends BaseController {
    @Resource
    private AdminService adminService;
    @Resource
    private UserService userService;
    @Resource
    private PatentService patentService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private NewsService newsService;
    @Resource
    private Category category;

    /**
     * 管理员登录
     * -------测试通过
     *
     * @param name
     * @param password
     * @return
     */
    @RequestMapping(value = "/admin.action", method = RequestMethod.POST)
    public ResponseResult login(@RequestParam @NotBlank String name, @RequestParam @NotBlank String password) {

        BaseResp baseResp = adminService.verityPassword(name, password);
        return setResult(baseResp);
    }

    /**
     * 修改密码
     *
     * @param passwordChangeParams 密码信息
     * @return
     */
    @RequestMapping(value = "/changeAdminPwd.action", method = RequestMethod.POST)
    public ResponseResult changeAdminPassWord(@RequestBody PasswordChangeParams passwordChangeParams) {
        BaseResp baseResp = adminService.changePasswd(passwordChangeParams);
        return setResult(baseResp);
    }

    /**
     * 添加行业类型
     * --------通过
     *
     * @param req
     * @return
     */
    @RequestMapping(value = "/addCategory.action", method = RequestMethod.POST)
    public Object addCategory(HttpServletRequest req) {
        JSONObject jsonObject = new JSONObject();
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
    @RequestMapping(value = "/getAllCategory.action", method = RequestMethod.GET)
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
    @RequestMapping(value = "/getPageCategory.action", method = RequestMethod.GET)
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
    @RequestMapping(value = "/deleteCategory.action", method = RequestMethod.POST)
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
    @RequestMapping(value = "/editCategory.action", method = RequestMethod.POST)
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
    @RequestMapping(value = "/countAll.action", method = RequestMethod.GET)
    public Object countAll() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userCount", userService.countUser());
        jsonObject.put("patentCount", patentService.countPatent());
        jsonObject.put("categoryCount", categoryService.countCategory());
        jsonObject.put("newsCount", newsService.countNews());
        return jsonObject;
    }

}

