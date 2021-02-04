package com.tang.patent.entity.bean;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Admin {

    private Long pkId;

    private String username;

    private String password;

    private Date createTime;

    private Date modifyTime;

    private Boolean flag;
}