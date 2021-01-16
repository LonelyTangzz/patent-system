package com.tang.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/7.
 * @name: AdminLoginParams
 * @description: 用户登陆参数
 */
@Data
@ApiModel(value = "PasswordChangeParams", description = "密码修改入参")
public class PasswordChangeParams implements Serializable {
    @NotBlank
    @ApiModelProperty(value = "账号", example = "admin")
    private String name;

    @NotBlank
    @ApiModelProperty(value = "原密码", example = "****")
    private String usedPassword;

    @NotBlank
    @ApiModelProperty(value = "现密码", example = "***")
    private String nowPassword;
}
