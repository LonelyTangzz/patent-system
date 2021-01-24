package com.tang.api;

import com.tang.basic.ResponseResult;
import com.tang.params.user.RegisterParams;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
    ResponseResult getVerifyCode(@NotBlank @RequestParam("phoneNum") String phoneNum, @NotBlank @RequestParam("type") String type);
}
