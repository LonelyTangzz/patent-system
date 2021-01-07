package com.example.patent.entity.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/7.
 * @name: AdminLoginParams
 * @description: 用户登陆参数
 */
@Data
public class PasswordChangeParams {
    @NotBlank
    private String name;
    @NotBlank
    private String usedPassword;
    @NotBlank
    private String nowPassword;
}
