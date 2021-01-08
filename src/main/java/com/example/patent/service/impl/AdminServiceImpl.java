package com.example.patent.service.impl;

import com.example.patent.entity.vo.UserInfoVo;
import com.google.common.collect.Lists;
import com.example.patent.common.MD5;
import com.example.patent.dao.AdminMapper;
import com.example.patent.entity.basic.BaseResp;
import com.example.patent.entity.basic.ResultType;
import com.example.patent.entity.bean.Admin;
import com.example.patent.entity.param.PasswordChangeParams;
import com.example.patent.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
//import java.util.List;


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
    public BaseResp<UserInfoVo> verityPassword(String name, String password) {
        BaseResp<UserInfoVo> baseResp = new BaseResp<>();
        //将传入密码进行md5加密后与数据库中存储值进行比较
        String realPwd = MD5.getInstance().getMD5ofStr(password);
        Integer id = adminMapper.verifyPassword(name, realPwd);
        if (id != null) {
            baseResp.setRespData(Lists.newArrayList(new UserInfoVo(id, name)));
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