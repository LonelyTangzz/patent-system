package com.example.patent.entity.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Data
@ToString
@Repository
public class Admin {

    private Integer id;

    private String username;

    private String password;

    private Date createTime;

    private Date modifyTime;

    private Integer flag;
}