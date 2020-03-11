package com.example.patent.dao;

import com.example.patent.bean.Patent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Patent record);

    int insertSelective(Patent record);

    Patent selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Patent record);

    int updateByPrimaryKey(Patent record);

    List<Patent> selectByName(@Param("name") String name);

    int selectIdByName(@Param("name")String name);

    int updateUserImg(Patent record);

    List<Patent> getAllPatent();

    int countPatent();
}