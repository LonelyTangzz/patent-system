package com.tang.patent.service.impl;

import com.tang.patent.entity.bean.Category;
import com.tang.patent.dao.CategoryMapper;
import com.tang.patent.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @name CategoryServiceImpl
 * @author tangzy
 * @since 2021/1/9
 * @version 1.0
 * @description: 分类管理业务层
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Boolean insert(Category category) {
        return categoryMapper.insert(category) > 0 ? true : false;
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
        return categoryMapper.deleteByPrimaryKey(id) > 0 ? true : false;
    }

    @Override
    public Boolean updateCategory(Category category) {
        return categoryMapper.updateByPrimaryKey(category) > 0 ? true : false;
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
