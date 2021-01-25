package com.tang.api;

import com.tang.basic.ResponseResult;
import com.tang.params.user.RegisterParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author MrTang
 * @version 1.0
 * @date 2021/1/24
 * @name UserApi
 * @description: 用户接口
 */
@Valid
@Api(value = "用户接口", tags = "【用户】接口")
public interface UserApi {

    /**
     * 用户注册接口
     *
     * @param registerParams 注册入参
     * @return 操作结果
     */
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "register.action", method = RequestMethod.POST)
    ResponseResult register(@Valid @ModelAttribute RegisterParams registerParams);

    /**
     * 获取验证短信接口
     *
     * @param phoneNum 电话号码
     * @param type     获取的短信类型
     * @return 返回结果
     */
    @ApiOperation(value = "获取短信接口")
    @RequestMapping(value = "getVerifyCode", method = RequestMethod.POST)
    ResponseResult getVerifyCode(@NotBlank @RequestParam @ApiParam("手机号") String phoneNum, @NotBlank @RequestParam @ApiParam("获取短信的操作类型") String type);

    /**
     * 用户登录
     *
     * @param username 用户账号
     * @param password 用户密码
     * @return 操作结果
     */
    @RequestMapping(value = "login.action", method = RequestMethod.POST)
    ModelAndView login(@NotBlank @RequestParam @ApiParam("用户账号") String username, @NotBlank @RequestParam @ApiParam("用户密码") String password);
}
