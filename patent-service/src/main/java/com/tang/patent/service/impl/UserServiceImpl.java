package com.tang.patent.service.impl;

import com.tang.basic.BaseResp;
import com.tang.basic.ResultType;
import com.tang.params.user.RegisterParam;
import com.tang.patent.common.Constants;
import com.tang.patent.entity.bean.User;
import com.tang.patent.dao.UserMapper;
import com.tang.patent.service.UserService;
import com.tang.patent.tools.MD5;
import com.tang.patent.tools.SendSms;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author tangzy
 * @version 1.0
 * @name UserServiceImpl
 * @description: 用户服务层
 * @since 2021/1/9
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private SendSms sendSms;
    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 用户注册业务
     *
     * @param registerParams 注册入参
     * @return 操作结果
     */
    @Override
    public BaseResp registerUser(RegisterParam registerParams) {
        BaseResp resp = new BaseResp();
        //toUpperCase将小写字母全体转大写
        if (!stringRedisTemplate.opsForValue().get(Constants.SMS_REGISTER_PREFIX.concat(registerParams.getPhoneNum())).equals(registerParams.getVerifyCode().toUpperCase())) {
            resp.setResultType(ResultType.INSERT_FAIL);
            return resp;
        }
        User user = new User();
        BeanUtils.copyProperties(registerParams, user);
        user.setPassword(MD5.getInstance().getMD5ofStr(user.getPassword()));
        user.setAvatar("/img_avatar.png");
        user.setCreateTime(new Date());
        user.setLoginTime(new Date());
        if (userMapper.insertSelective(user) <= 0) {
            resp.setResultType(ResultType.INSERT_FAIL);
        }
        return resp;
    }

    /**
     * 用户获取短信业务
     *
     * @param phoneNum 手机号
     * @param type     短信获取类型
     * @return 操作结果
     */
    @Override
    public BaseResp getVerifyCode(String phoneNum, String type) {
        BaseResp resp = new BaseResp();
        //发送短信的业务代码交给sendSms工具类处理 type:0-注册短信,1-重置密码短信
        try {
            switch (type) {
                case Constants.SMS_REGISTER: {
                    sendSms.register(phoneNum);
                    break;
                }
                case Constants.SMS_RESET: {
                    sendSms.forgetPassword(phoneNum);
                    break;
                }
                default:
                    throw new Exception();
            }
        } catch (Exception e) {
            resp.setResultType(ResultType.INSERT_FAIL);
        }
        return resp;
    }

    /**
     * 用户登录,验证用户信息
     *
     * @param name     账号
     * @param password 密码
     * @return
     */
    @Override
    public Boolean checkAccount(String name, String password) {
        User user = new User();
        user.setUsername(name);
        user.setPassword(MD5.getInstance().getMD5ofStr(password));
        user.setLoginTime(new Date());
        return userMapper.checkAccount(user) > 0;
    }

    /**
     * 验证手机验证码是否正确
     *
     * @param phoneNum   电话号码
     * @param verifyCode 验证码
     * @return 是否正确
     */
    @Override
    public Boolean checkVerifyCode(String phoneNum, String verifyCode) {
        return Objects.equals(stringRedisTemplate.opsForValue().get(Constants.SMS_RESET_PREFIX.concat(phoneNum)), verifyCode.toUpperCase());
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
        User user = new User();
        int id = userMapper.selectIdByName(name);
        user.setPassword(password);
        user.setId(id);
        return userMapper.updateByPrimaryKeySelective(user) > 0;
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
        return userMapper.updateUserImg(user) > 0;
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
        return userMapper.updateByPrimaryKeySelective(user) > 0;
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
