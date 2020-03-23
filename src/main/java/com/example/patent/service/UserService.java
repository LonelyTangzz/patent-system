package com.example.patent.service;


import com.example.patent.bean.User;

import java.util.List;

public interface UserService {
    boolean checkAccount(String name,String password);

    boolean addUser(String name,String password);

    boolean updatePassword(String name,String password);

    boolean updateUserAvatar(User user);

    int getUserIdByName(String name);


    boolean updateUserInfo(User user);

    int countUser();

    List<User> getPageUser(int num);
}
