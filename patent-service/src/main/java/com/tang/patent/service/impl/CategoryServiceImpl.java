package com.tang.patent.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.tang.patent.entity.bean.Category;
import com.tang.patent.dao.CategoryMapper;
import com.tang.patent.service.CategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public Boolean insert(String categoryName) {
        Category category = new Category();
        category.setCategory(categoryName);
        category.setId(IdWorker.getId());
        return categoryMapper.insert(category) > 0;
    }

    @Override
    public String findCategory(Integer id) {
        String categoryName = categoryMapper.selectByPrimaryKey(id).getCategory();
        return categoryName;
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryMapper.getAllCategory();
    }

    @Override
    public Boolean deleteCategory(Integer id) {
//        return categoryMapper.deleteByPrimaryKey(id) > 0;
        return true;
    }

    @Override
    public Boolean updateCategory(Category category) {
//        return categoryMapper.updateByPrimaryKey(category) > 0;
        return true;
    }

    @Override
    public int countCategory() {
        return categoryMapper.countCategory();
    }

    @Override
    public List<Category> getPageCategory(Integer page) {
        return categoryMapper.getPageCategory(page);
    }
}
