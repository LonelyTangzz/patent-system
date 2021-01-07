package com.example.patent.entity.basic;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/7.
 * @name: ResultType
 * @description: 返回类型枚举
 */
public enum ResultType {
    /**
     * 成功状态码value为1,失败为0,desc中放置的描述
     */
    SUCCESS(1, "操作成功"),
    UPDATE_FAIL(0, "更新失败"),
    DELETE_FAIL(0, "删除失败"),
    INSERT_FAIL(0, "添加失败"),
    VERIFY_FAIL(0,"账号密码错误!");

    private int value;

    private String desc;

    private ResultType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
