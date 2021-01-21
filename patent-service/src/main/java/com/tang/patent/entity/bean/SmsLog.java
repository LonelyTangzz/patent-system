package com.tang.patent.entity.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MrTang
 * @version 1.0
 * @date 2021/1/17
 * @name SmsLog
 * @description: 短信发送日志实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sms_log")
public class SmsLog implements Serializable {

    @TableId("pkId")
    private Long pkId;

    @TableField("phone")
    private String phone;

    @TableField("action")
    private String action;

    @TableField("createTime")
    private Date createTime;
}
