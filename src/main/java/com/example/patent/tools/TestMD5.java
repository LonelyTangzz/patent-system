package com.example.patent.tools;

import com.example.patent.common.MD5;
import org.junit.Test;

public class TestMD5 {
    @Test
    public void Test() {
        String md5ofStr = MD5.getInstance().getMD5ofStr("123");
        System.out.println("md5ofStr = " + md5ofStr);
    }
}
