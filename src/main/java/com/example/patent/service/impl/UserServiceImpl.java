package com.example.patent.service.impl;

import com.example.patent.bean.User;
import com.example.patent.dao.UserMapper;
import com.example.patent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
     * @param name     账号
     * @param password 密码
     * @return
     */
    @Override
    public boolean addUser(String name, String password) {
        user.setUsername(name);
        user.setPassword(password);
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
    public List<User> listAllUser() {
        return userMapper.listAllUser();
    }

    @Override
    public boolean updateUserInfo(User user) {
        return userMapper.updateByPrimaryKeySelective(user) > 0 ? true : false;
    }

    @Override
    public int countUser() {
        return userMapper.countUser();
    }
}
