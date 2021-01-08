package com.example.patent.dao;

import com.example.patent.entity.bean.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {


    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    Integer verifyPassword(@Param(value = "username") String username, @Param(value = "password") String password);

    Integer changePassword(@Param(value = "username") String username, @Param(value = "password") String password);
}