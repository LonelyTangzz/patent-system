package com.tang.patent.dao;

import com.tang.patent.entity.bean.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    Integer insert(Category category);


    Category selectByPrimaryKey(Integer id);


    List<Category> getAllCategory();

    Integer countCategory();

    List<Category> getPageCategory(Integer page);

    Boolean updateByPrimaryKey(Category category);

    Boolean deleteCategory(Long id);

}