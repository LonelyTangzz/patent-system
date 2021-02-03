package com.tang.params.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/2/3.
 * @name: ResetPasswordParam
 * @description: 重置密码请求参数
 */
@Data
@ApiModel(value = "重置密码请求参数", description = "根据验证码重置用户信息最后一步的入参")
public class ResetPasswordParam implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "用户账号", example = "bigger")
    private String username;

    @NotBlank
    @ApiModelProperty(value = "重置后的密码", example = "999999")
    private String password;

    @NotBlank
    @ApiModelProperty(value = "手机号码", example = "1890658xxxx")
    private String phoneNum;

    @NotBlank
    @ApiModelProperty(value = "验证码", example = "EF354A")
    private String verifyCode;
}
