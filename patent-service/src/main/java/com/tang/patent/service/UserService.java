package com.tang.patent.service;


import com.tang.basic.BaseResp;
import com.tang.params.user.RegisterParams;
import com.tang.patent.entity.bean.User;

import java.util.List;
import java.util.Map;
/**
 * @name UserService
 * @author tangzy
 * @since 2021/1/24
 * @version 1.0
 * @description: 用户业务层
 */
public interface UserService {
    /**
     * 用户注册业务
     *
     * @param registerParams 注册入参
     * @return 操作结果
     */
    BaseResp registerUser(RegisterParams registerParams);

    /**
     * 发送验证码信息
     *
     * @param phoneNum 手机号
     * @param type 短信获取类型
     * @return 操作结果
     */
    BaseResp getVerifyCode(String phoneNum,String type);

    boolean checkAccount(String name, String password);

    boolean addUser(User user);

    boolean updatePassword(String name, String password);

    boolean updateUserAvatar(User user);

    int getUserIdByName(String name);

    boolean updateUserInfo(User user);

    int countUser();

    List<User> getPageUser(int num);

    List<Map<String, Object>> countUserByMonth(String year, String month);

    String getUserPhoneByName(String username);

    User getUserByName(String username);
}
