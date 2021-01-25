package com.tang.vos.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/8.
 * @name: UserInfoVo
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserInfoVo", description = "用户")
public class AdminInfoVo implements Serializable {

    @ApiModelProperty(value = "管理员id")
    private Integer id;

    @ApiModelProperty(value = "管理员账号")
    private String username;

}
