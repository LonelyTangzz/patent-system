package com.tang.vos.patent;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/2/4.
 * @name: PatentVo
 * @description: 专利信息返回参数
 */
@Data
@ApiModel(value = "PatentVo")
public class PatentVo implements Serializable {

    @ApiModelProperty(value = "主键", example = "111")
    private Long pkId;

    @ApiModelProperty("专利名")
    private String patentName;

    @ApiModelProperty("持有人")
    private String owner;

    @ApiModelProperty("上传时间")
    private Date updateTime;

    @ApiModelProperty("详情")
    private String details;

    @ApiModelProperty("所在地")
    private String location;

    @ApiModelProperty("专利编号")
    private String patentNo;

    @ApiModelProperty("分类id")
    private String category;

    @ApiModelProperty("价格")
    private Double price;

}
