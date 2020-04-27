package com.example.patent.tools;
import com.example.patent.dao.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

public class ServiceTest {
    @Autowired
    UserMapper userMapper;



    @Test
    public void ServiceConfig(){
        userMapper.countUser();
//        userMapper.countUserByMonth(2);
    }
}
