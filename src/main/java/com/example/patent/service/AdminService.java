package com.example.patent.service;

import com.example.patent.entity.basic.BaseResp;
import com.example.patent.entity.param.PasswordChangeParams;

/**
 * 用户管理业务层
 */
public interface AdminService {

    /**
     * 验证密码是否正确
     *
     * @param name     账号
     * @param password 密码
     * @return 操作结果
     */
    BaseResp verityPassword(String name, String password);

    /**
     * 修改密码
     *
     * @param passwordChangeParams 修改信息
     * @return 操作结果
     */
    BaseResp changePasswd(PasswordChangeParams passwordChangeParams);
}
