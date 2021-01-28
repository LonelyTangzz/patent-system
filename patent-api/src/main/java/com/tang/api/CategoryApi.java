package com.tang.api;

import com.tang.basic.ResponseResult;
import com.tang.vos.category.CategoryGetByPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    ResponseResult addCategory(@RequestParam @NotBlank @ApiParam("类别名") String categoryName);

    /**
     * 分页获取类别信息
     *
     * @param pageNum 页码
     * @return 该页信息
     */
    @ApiOperation(value = "分页获取类别", notes = "分页获取类别操作")
    @RequestMapping(value = "/page/get", method = RequestMethod.GET)
    ResponseResult<CategoryGetByPageVo> getCategoryByPage(@RequestParam @ApiParam("页码") Integer pageNum);

    /**
     * 修改类别信息操作
     *
     * @param pkId         类别主键
     * @param categoryName 类别名
     * @return 操作结果
     */
    @ApiOperation(value = "修改类别信息", notes = "修改类别信息操作")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    ResponseResult editCategory(@RequestParam @NotNull @ApiParam("类别id") Long pkId, @RequestParam @NotBlank @ApiParam("新类别名") String categoryName);

    /**
     * 删除类别操作
     *
     * @param pkId 类别主键
     * @return 操作结果
     */
    @ApiOperation(value = "删除类别", notes = "删除类别操作(软删除)")
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    ResponseResult deleteCategory(@RequestParam Long pkId);

}
