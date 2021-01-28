package com.tang.vos.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/28.
 * @name: CategoryVo
 * @description: 类别返回体
 */
@Data
@ApiModel(value = "CategoryVo",description = "类别返回体")
public class CategoryVo implements Serializable {

    @ApiModelProperty("主键id")
    private Long pkId;

    @ApiModelProperty("类别名")
    private String CategoryName;
}
