package com.example.patent.service;


import com.example.patent.bean.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    boolean checkAccount(String name,String password);

    boolean addUser(User user);

    boolean updatePassword(String name,String password);

    boolean updateUserAvatar(User user);

    int getUserIdByName(String name);

    boolean updateUserInfo(User user);

    int countUser();

    List<User> getPageUser(int num);

    List<Map<String, Object>> countUserByMonth(String year,String month);

    String getUserPhoneByName(String username);

    User getUserByName(String username);
}
