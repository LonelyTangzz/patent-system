package com.tang.patent.dao;

import com.tang.patent.bean.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);

    int verifyPassword(@Param(value = "username") String username, @Param(value = "password") String password);

    int changePassword(@Param(value = "username") String username, @Param(value = "password") String password);
}