package com.tang.api;

import com.tang.basic.ResponseResult;
import com.tang.params.admin.PasswordChangeParams;
import com.tang.vos.admin.AdminInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author MrTang
 * @version 1.0
 * @date 2021/1/16
 * @name AdminApi
 * @description: 管理员操作接口
 */
@Valid
@RequestMapping("/admin")
@Api(value = "管理员接口", tags = "【管理员】接口")
public interface AdminApi {

    /**
     * 管理员登录
     * -------测试通过
     *
     * @param username 用户名
     * @param password 密码
     * @return 操作结果
     */
    @ApiOperation(value = "管理员登录", notes = "管理登录操作")
    @RequestMapping(value = "/login.action", method = RequestMethod.POST)
    ResponseResult<AdminInfoVo> login(@RequestParam @NotBlank @ApiParam("用户账号") String username, @RequestParam @NotBlank @ApiParam("密码") String password);

    /**
     * 修改密码
     *
     * @param passwordChangeParams 密码信息
     * @return 操作结果
     */
    @ApiOperation(value = "修改管理员密码")
    @RequestMapping(value = "/password/change.action", method = RequestMethod.POST)
    ResponseResult changeAdminPassWord(@RequestBody @Validated PasswordChangeParams passwordChangeParams);


}
