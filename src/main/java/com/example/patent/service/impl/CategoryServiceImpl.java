package com.example.patent.service.impl;

import com.example.patent.bean.Category;
import com.example.patent.dao.CategoryMapper;
import com.example.patent.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
