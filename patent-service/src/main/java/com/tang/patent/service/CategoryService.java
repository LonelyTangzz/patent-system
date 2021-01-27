package com.tang.patent.service;

import com.tang.patent.entity.bean.Category;

import java.util.List;

public interface CategoryService {
    String findCategory(Integer id);

    Boolean insert(String categoryName);

    List<Category> getAllCategory();

    Boolean deleteCategory(Integer id);

    Boolean updateCategory(Category category);

    int countCategory();

    List<Category> getPageCategory(Integer page);
}
