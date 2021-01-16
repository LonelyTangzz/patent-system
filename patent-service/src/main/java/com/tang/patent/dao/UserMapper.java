package com.tang.patent.dao;

import com.tang.patent.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkAccount(User record);

    int selectIdByName(@Param("username") String username);

    int updateUserImg(User record);

    List<User> getPageUser(Integer page);

    int countUser();

    List<Map<String, Object>> countUserByMonth(Integer year, Integer month);

}