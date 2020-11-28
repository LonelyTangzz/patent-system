package com.tang.patent.service.impl;

import com.tang.patent.bean.User;
import com.tang.patent.dao.UserMapper;
import com.tang.patent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    User user = new User();

    /**
     * 用户登录
     *
     * @param name     账号
     * @param password 密码
     * @return
     */
    @Override
    public boolean checkAccount(String name, String password) {
        user.setUsername(name);
        user.setPassword(password);
        user.setLoginTime(new Date());
        return userMapper.checkAccount(user) > 0 ? true : false;
    }

    /**
     * 用户注册
     *
     * @return
     */
    @Override
    public boolean addUser(User user) {
        user.setCreateTime(new Date());
        user.setLoginTime(new Date());
        return userMapper.insert(user) > 0 ? true : false;
    }

    /**
     * 用户信息补全----还没做
     *
     * @param name     账号
     * @param password 密码
     * @return
     */
    @Override
    public boolean updatePassword(String name, String password) {
        int id = userMapper.selectIdByName(name);
        user.setPassword(password);
        user.setId(id);
        return userMapper.updateByPrimaryKeySelective(user) > 0 ? true : false;
    }

    /**
     * 获取用户id
     *
     * @param name 账号
     * @return
     */
    @Override
    public int getUserIdByName(String name) {
        return userMapper.selectIdByName(name);
    }

    /**
     * 更新用户头像
     *
     * @param user 用户实体
     * @return
     */
    @Override
    public boolean updateUserAvatar(User user) {
        return userMapper.updateUserImg(user) > 0 ? true : false;
    }

    /**
     * 显示所有用户信息
     *
     * @return
     */

    @Override
    public List<User> getPageUser(int num) {
        return userMapper.getPageUser(num);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public boolean updateUserInfo(User user) {
        return userMapper.updateByPrimaryKeySelective(user) > 0 ? true : false;
    }

    /**
     * 统计用户数量
     *
     * @return
     */
    @Override
    public int countUser() {
        return userMapper.countUser();
    }

    /**
     * 根据当前月份统计新增用户
     *
     * @param year
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Object>> countUserByMonth(String year, String month) {
        return userMapper.countUserByMonth(Integer.parseInt(year), Integer.parseInt(month));
    }

    /**
     * 忘记密码时获取用户密码
     *
     * @param username
     * @return
     */
    @Override
    public String getUserPhoneByName(String username) {
        return userMapper.selectByPrimaryKey(userMapper.selectIdByName(username)).getPhoneNum();
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @Override
    public User getUserByName(String username) {
        return userMapper.selectByPrimaryKey(userMapper.selectIdByName(username));
    }
}
