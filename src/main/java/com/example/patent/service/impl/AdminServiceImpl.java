package com.example.patent.service.impl;

import com.example.patent.dao.AdminMapper;
import com.example.patent.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean veritypasswd(String name, String password) {
        return adminMapper.verifyPassword(name, password)>0?true:false;
    }

}