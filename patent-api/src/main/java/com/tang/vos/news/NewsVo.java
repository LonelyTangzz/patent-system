package com.tang.vos.news;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/2/4.
 * @name: newsVo
 * @description:
 */
@Data
@ApiModel
public class NewsVo implements Serializable {

    @ApiModelProperty("主键")
    private Long pkId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("详情")
    private String details;

    @ApiModelProperty("上传时间")
    private Date updateTime;

    @ApiModelProperty("图片")
    private String img;
}
