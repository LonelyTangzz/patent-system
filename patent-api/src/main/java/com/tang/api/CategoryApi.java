package com.tang.api;

import com.tang.basic.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/27.
 * @name: CategoryApi
 * @description: 专利及新闻分类API接口
 */
@Valid
@RequestMapping("/category")
@Api(value = "新闻及专利类型接口", tags = "【类别】接口")
public interface CategoryApi {

    /**
     * 添加分类接口
     *
     * @param categoryName 分类名
     * @return 操作结果
     */
    @ApiOperation(value = "添加分类", notes = "添加类别操作")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    ResponseResult addCategory(@RequestParam @ApiParam("类别名") String categoryName);

}
