package com.tang.patent.entity.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Repository;

import java.util.Date;
/**
 * @name User
 * @author tangzy
 * @since 2021/1/24
 * @version 1.0
 * @description:
 */
@Data
@ToString
@Repository
public class User {
    private Long pkId;

    private String username;

    private String password;

    private Byte sex;

    private String phoneNum;

    private String email;
    
    private Date birth;

    private String location;

    private String avatar;

    private Date createTime;

    private Date loginTime;

    private String realName;

}