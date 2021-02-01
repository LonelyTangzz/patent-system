package com.tang.patent.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tang.basic.BaseResp;
import com.tang.basic.ResultType;
import com.tang.patent.entity.bean.Category;
import com.tang.patent.dao.CategoryMapper;
import com.tang.patent.service.CategoryService;
import com.tang.vos.category.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangzy
 * @version 1.0
 * @name CategoryServiceImpl
 * @description: 分类管理业务层
 * @since 2021/1/9
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    /**
     * 新增分类
     *
     * @param categoryName 分类名
     * @return 操作结果
     */
    @Override
    public BaseResp insert(String categoryName) {
        BaseResp baseResp = new BaseResp(ResultType.INSERT_FAIL);
        Category category = new Category();
        category.setCategoryName(categoryName);
        category.setPkId(IdWorker.getId());
        if (categoryMapper.insert(category) > 0) {
            baseResp.setResultType(ResultType.SUCCESS);
        }
        return baseResp;
    }

    @Override
    public String findCategory(Integer id) {
        String categoryName = categoryMapper.selectByPrimaryKey(id).getCategoryName();
        return categoryName;
    }

    @Override
    public List<CategoryVo> getAllCategory() {
        List<CategoryVo> result = categoryMapper.getAllCategory();
        return result;
    }

    /**
     * 删除分类(软删除)
     *
     * @param pkId 类主键
     * @return 操作结果
     */
    @Override
    public BaseResp deleteCategory(Long pkId) {
        BaseResp baseResp = new BaseResp(ResultType.DELETE_FAIL);
        if (categoryMapper.deleteCategory(pkId)) {
            baseResp.setResultType(ResultType.SUCCESS);
        }
        return baseResp;
    }

    /**
     * 修改类别信息
     *
     * @param category 类别信息
     * @return 操作结果
     */
    @Override
    public BaseResp updateCategory(Category category) {
        BaseResp baseResp = new BaseResp(ResultType.UPDATE_FAIL);
        if (categoryMapper.updateByPrimaryKey(category)) {
            baseResp.setResultType(ResultType.SUCCESS);
        }
        return baseResp;
    }

    /**
     * 获取类别总数
     *
     * @return 类别总数
     */
    @Override
    public Integer countCategory() {
        return categoryMapper.countCategory();
    }

    /**
     * 分页获取类别信息
     *
     * @param page 页码
     * @return 类别信息
     */
    @Override
    public List<CategoryVo> getPageCategory(Integer page) {
        List<Category> categoryList = categoryMapper.getPageCategory((page - 1) * 5);
        List<CategoryVo> categoryGetByPageVoList = new ArrayList<>();
        for (Category category : categoryList) {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category, categoryVo);
            categoryGetByPageVoList.add(categoryVo);
        }
        return categoryGetByPageVoList;
    }
}
