package com.tang.patent.dao;

import com.tang.patent.bean.Patent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PatentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Patent record);

    int insertSelective(Patent record);

    Patent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Patent record);

    int updateByPrimaryKey(Patent record);

    List<Patent> selectByName(@Param("name") String name);

    int selectIdByName(@Param("name") String name);

    int updateUserImg(Patent record);

    List<Patent> getPatentByPage(@Param("page") Integer page);

    List<Patent> getPatentByCategory(String category, Integer page);

    int countPatent();

    int updateByNo(Patent record);

    List<Map<String, Object>> countPatentOrderByCategory();

}