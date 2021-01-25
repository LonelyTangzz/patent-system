package com.tang.patent.service.impl;

import com.tang.vos.admin.AdminInfoVo;
import com.google.common.collect.Lists;
import com.tang.patent.tools.MD5;
import com.tang.patent.dao.AdminMapper;
import com.tang.params.admin.PasswordChangeParams;
import com.tang.patent.service.AdminService;
import com.tang.basic.BaseResp;
import com.tang.basic.ResultType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @name: AdminServiceImpl
 * @author: tangzy
 * @since: 2021/1/7
 * @version: 1.0
 * @description: 管理员服务层
 */
@Service
class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    /**
     * 验证账号密码是否匹配
     *
     * @param name     账号
     * @param password 密码
     * @return 操作结果
     */
    @Override
    public BaseResp<AdminInfoVo> verityPassword(String name, String password) {
        BaseResp<AdminInfoVo> baseResp = new BaseResp<>();
        //将传入密码进行md5加密后与数据库中存储值进行比较
        String realPwd = MD5.getInstance().getMD5ofStr(password);
        Integer id = adminMapper.verifyPassword(name, realPwd);
        if (id != null) {
            baseResp.setRespData(Lists.newArrayList(new AdminInfoVo(id, name)));
        } else {
            baseResp.setResultType(ResultType.VERIFY_FAIL);
        }
        return baseResp;
    }

    /**
     * 修改密码
     *
     * @param passwordChangeParams 修改信息
     * @return 操作结果
     */
    @Override
    public BaseResp changePasswd(PasswordChangeParams passwordChangeParams) {
        //将传入密码进行md5加密后与数据库中存储值进行比较
        String usedPassword = MD5.getInstance().getMD5ofStr(passwordChangeParams.getUsedPassword());
        //验证密码是否正确
        boolean res = adminMapper.verifyPassword(passwordChangeParams.getName(), usedPassword) > 0;
        BaseResp baseResp = new BaseResp();
        if (!res) {
            baseResp.setResultType(ResultType.VERIFY_FAIL);
        }
        //将新密码加密
        String password = MD5.getInstance().getMD5ofStr(passwordChangeParams.getNowPassword());
        //修改密码
        if (adminMapper.changePassword(passwordChangeParams.getName(), password) < 0) {
            baseResp.setResultType(ResultType.UPDATE_FAIL, "修改管理员用户密码出现异常");
        }
        return baseResp;
    }

}