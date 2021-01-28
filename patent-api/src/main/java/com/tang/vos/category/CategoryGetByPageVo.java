package com.tang.vos.category;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/28.
 * @name: CategoryGetByPageVo
 * @description: 分页获取类别返回体
 */
@Data
@ApiModel(value = "CategoryGetByPageVo",description = "分页获取类别返回体")
public class CategoryGetByPageVo implements Serializable {

    @ApiModelProperty("总页数")
    private Integer totalPage;

    @ApiModelProperty("该页中类别信息")
    private List<CategoryVo> categoryList;
}
