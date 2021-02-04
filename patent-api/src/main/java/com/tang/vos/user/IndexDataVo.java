package com.tang.vos.user;

import com.tang.vos.category.CategoryVo;
import com.tang.vos.news.NewsVo;
import com.tang.vos.patent.PatentVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/2/4.
 * @name: IndexDataVo
 * @description: 首页显示数据返回数据
 */
@Data
@ApiModel(value = "IndexDataVo",description = "首页显示数据返回数据")
public class IndexDataVo implements Serializable {

    @ApiModelProperty(value = "分类",example = "电子硬件")
    private List<CategoryVo> categories;

    @ApiModelProperty(value = "专利信息", example = "磁保持继电器")
    private List<PatentVo> patents;

    @ApiModelProperty(value = "新闻资讯", example = "xxxx")
    private List<NewsVo> news;

    @ApiModelProperty(value = "专利总数", example = "40")
    private Integer patentCount;

    @ApiModelProperty(value = "新闻总数", example = "50")
    private Integer newsCount;

}
