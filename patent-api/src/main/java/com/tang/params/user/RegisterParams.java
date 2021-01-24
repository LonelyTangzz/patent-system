package com.tang.params.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author MrTang
 * @version 1.0
 * @date 2021/1/24
 * @name RegisterParams
 * @description: 用户注册入参
 */
@Data
@ApiModel(value = "用户注册入参",description = "用户进行注册时提交的表单")
public class RegisterParams implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "注册账号", example = "1111")
    private String username;

    @NotBlank
    @ApiModelProperty(value = "用户密码",example = "99999")
    private String password;

    @NotBlank
    @ApiModelProperty(value = "绑定电话号码", example = "1890658xxxx")
    private String phoneNum;

    @NotBlank
    @ApiModelProperty(value = "短信验证码",example = "A4BSC3")
    private String verifyCode;

}
