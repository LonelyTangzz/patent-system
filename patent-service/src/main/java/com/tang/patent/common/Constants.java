package com.tang.patent.common;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/7.
 * @name: Constant
 * @description: 常量类--用于放置常量
 */
public class Constants {

    public static final String ADMIN_PREFIX = "";

    public static final String USER_PREFIX = "";

    public static final String PATENT_PREFIX = "";

    public static final String NEWS_PREFIX = "";

    /**
     * 区别操作与页面的后缀
     */
    public static final String ACTION_SUFFIX = ".action";
    /**
     * 注册短信存于redis中键的前缀
     */
    public static final String SMS_REGISTER_PREFIX = "sms_tel_register:";

    /**
     * 重置密码短信存于redis中键的前缀
     */
    public static final String SMS_RESET_PREFIX = "sms_tel_reset:";

    /**
     * 用户使用短信的注册操作类型
     */
    public static final String SMS_REGISTER = "0";

    /**
     * 用户使用短信的重置操作类型
     */
    public static final String SMS_RESET = "1";
}
