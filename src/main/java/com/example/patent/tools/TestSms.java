package com.example.patent.tools;

import com.example.patent.common.SendSms;

public class TestSms {
    //以下为测试代码，随机生成验证码
    private static int newcode;
    public static void main(String[] args) throws Exception {
        newcode = (int)(Math.random()*9999)+100;
        String code = Integer.toString(newcode);
        SendSms sendSms = new SendSms();
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + sendSms.register("18406587359",code));
    }
}
