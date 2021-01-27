package com.tang.patent.dao;

import com.tang.patent.entity.bean.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    int insert(Category category);


    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);


    List<Category> getAllCategory();

    int countCategory();

    List<Category> getPageCategory(Integer page);

    Boolean updateByPrimaryKey(Category category);

    Boolean deleteByPrimaryKey(Integer id);
}