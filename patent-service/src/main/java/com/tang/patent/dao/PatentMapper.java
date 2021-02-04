package com.tang.patent.dao;

import com.tang.patent.entity.bean.Patent;
import com.tang.vos.patent.PatentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
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

    List<PatentVo> getPatentByPage(@Param("page") Integer page);

    List<Patent> getPatentByCategory(String category, Integer page);

    int countPatent();

    int updateByNo(Patent record);

    List<Map<String, Object>> countPatentOrderByCategory();

}