package com.example.patent.service;

public interface AdminService {
    boolean veritypasswd(String name, String password);

    boolean changePasswd(String name, String password);
}
