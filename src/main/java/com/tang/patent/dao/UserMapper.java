package com.tang.patent.dao;

import com.tang.patent.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 用户数据层方法
 *
 * @author MrTang
 * @version 1.0
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 根据id删除用户信息
     *
     * @param id 用户id
     * @return 操作结果
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入(新增)用户信息
     *
     * @param record 用户信息
     * @return 操作结果
     */
    int insert(User record);

    /**
     * 插入(新增)用户信息(无数据位置不录入)
     *
     * @param record 用户信息
     * @return 操作结果
     */
    int insertSelective(User record);

    /**
     * 根据用户id查询用户信息
     *
     * @param id 用户id
     * @return 操作结果
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 根据用户Id更新用户信息(无数据项不做更改)
     *
     * @param record 用户信息
     * @return 操作结果
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据用户Id更新信息(无数据项置为空)
     *
     * @param record 用户信息
     * @return 操作结果
     */
    int updateByPrimaryKey(User record);

    /**
     * 确认用户是否存在
     *
     * @param record 用户信息
     * @return 判断结果
     */
    int checkAccount(User record);

    /**
     * 根据用户名查询用户id
     *
     * @param username 用户名
     * @return 是否存在用户
     */
    int selectIdByName(@Param("username") String username);

    /**
     * 更新用户头像
     *
     * @param record 用户头像
     * @return 操作结果
     */
    int updateUserImg(User record);

    /**
     * 根据页码分页获取用户信息
     *
     * @param page 页码
     * @return 用户信息
     */
    List<User> getPageUser(Integer page);

    /**
     * 统计用户数量
     *
     * @return 用户数
     */
    int countUser();

    /**
     * 统计当前月份下每日的新增用户数量
     *
     * @param year  年份
     * @param month 月份
     * @return 用户数量
     */
    List<Map<String, Object>> countUserByMonth(Integer year, Integer month);

}