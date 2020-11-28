package com.tang.patent.service.impl;

import com.tang.patent.dao.AdminMapper;
import com.tang.patent.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean veritypasswd(String name, String password) {
        return adminMapper.verifyPassword(name, password) > 0 ? true : false;
    }

    @Override
    public boolean changePasswd(String name, String password) {
        return adminMapper.changePassword(name, password) > 0 ? true : false;
    }
}