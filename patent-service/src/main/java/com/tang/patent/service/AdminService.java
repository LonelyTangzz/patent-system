package com.tang.patent.service;

import com.tang.params.admin.PasswordChangeParam;
import com.tang.vos.admin.AdminInfoVo;
import com.tang.basic.BaseResp;

/**
 * 用户管理业务层
 * @author MrTang
 */
public interface AdminService {

    /**
     * 验证密码是否正确
     *
     * @param name     账号
     * @param password 密码
     * @return 操作结果
     */
    BaseResp<AdminInfoVo> verityPassword(String name, String password);

    /**
     * 修改密码
     *
     * @param passwordChangeParam 修改信息
     * @return 操作结果
     */
    BaseResp changePasswd(PasswordChangeParam passwordChangeParam);
}
