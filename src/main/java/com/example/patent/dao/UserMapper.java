package com.example.patent.dao;

import com.example.patent.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
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