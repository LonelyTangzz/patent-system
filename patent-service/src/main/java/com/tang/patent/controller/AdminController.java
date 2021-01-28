package com.tang.patent.controller;

import com.alibaba.fastjson.JSONObject;
import com.tang.api.AdminApi;
import com.tang.patent.common.Constants;
import com.tang.patent.entity.bean.Category;
import com.tang.params.admin.PasswordChangeParams;
import com.tang.vos.admin.AdminInfoVo;
import com.tang.patent.logger.LoggerUtils;
import com.tang.basic.BaseController;
import com.tang.basic.BaseResp;
import com.tang.basic.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import com.tang.patent.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @name: AdminController
 * @author: tangzy
 * @since: 2021/1/7
 * @version: 1.0
 * @description: 管理员控制层
 */
@Slf4j
@RestController
public class AdminController extends BaseController implements AdminApi {
    /**
     * 打印日志用
     */
    LoggerUtils logger = new LoggerUtils(this.getClass().getName());

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

    /**
     * 管理员登录
     * -------测试通过
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     */
    @Override
    public ResponseResult<AdminInfoVo> login(String username, String password) {
        logger.startLog();
        BaseResp<AdminInfoVo> baseResp = adminService.verityPassword(username, password);
        logger.endLog();
        return setResult(baseResp);
    }

    /**
     * 修改密码
     *
     * @param passwordChangeParams 密码信息
     * @return 操作结果
     */
    @Override
    public ResponseResult changeAdminPassWord(PasswordChangeParams passwordChangeParams) {
        logger.startLog();
        BaseResp baseResp = adminService.changePasswd(passwordChangeParams);
        logger.endLog();
        return setResult(baseResp);
    }

    /**
     * 获取所有行业分类
     * ----------通过
     *
     * @return
     */
    @RequestMapping(value = "/getAllCategory" + Constants.ACTION_SUFFIX, method = RequestMethod.GET)
    public Object getAllCategory() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("allCategory", categoryService.getAllCategory());
        return jsonObject;
    }

    /**
     * 获取所有数据数量
     */
    @RequestMapping(value = "/countAll" + Constants.ACTION_SUFFIX, method = RequestMethod.GET)
    public Object countAll() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userCount", userService.countUser());
        jsonObject.put("patentCount", patentService.countPatent());
        jsonObject.put("categoryCount", categoryService.countCategory());
        jsonObject.put("newsCount", newsService.countNews());
        return jsonObject;
    }
}

