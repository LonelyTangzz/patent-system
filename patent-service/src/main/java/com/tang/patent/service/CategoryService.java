package com.tang.patent.service;

import com.tang.basic.BaseResp;
import com.tang.patent.entity.bean.Category;
import com.tang.vos.category.CategoryGetByPageVo;
import com.tang.vos.category.CategoryVo;

import java.util.List;

/**
 * @name: CategoryService
 * @author: tangzy
 * @since: 2021/1/28
 * @version: 1.0
 * @description: 类别业务层
 */
public interface CategoryService {

    String findCategory(Integer id);

    /**
     * 新增分类
     *
     * @param categoryName 分类名
     * @return 操作结果
     */
    BaseResp insert(String categoryName);


    /**
     * 获取所有分类信息
     *
     * @return 分类信息
     */
    List<CategoryVo> getAllCategory();

    /**
     * 删除分类(软删除)
     *
     * @param pkId 类主键
     * @return 操作结果
     */
    BaseResp deleteCategory(Long pkId);

    /**
     * 修改类别信息
     *
     * @param category 类别信息
     * @return 操作结果
     */
    BaseResp updateCategory(Category category);

    /**
     * 获取类别总数
     *
     * @return 类别总数
     */
    Integer countCategory();

    /**
     * 分页获取类别信息
     *
     * @param page 页码
     * @return 类别信息
     */
    List<CategoryVo> getPageCategory(Integer page);
}
