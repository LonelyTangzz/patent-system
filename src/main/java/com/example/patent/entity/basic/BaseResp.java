package com.example.patent.entity.basic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/7.
 * @name: BaseResp
 * @description: 基础返回类
 */
public class BaseResp<T> implements Serializable {
    private ResultType resultType;
    private List<T> respData = new ArrayList<>();

    public BaseResp() {
        this.resultType = ResultType.SUCCESS;
    }

    public BaseResp(ResultType resultType) {
        this.resultType = resultType;
    }

    public BaseResp(ResultType resultType, T respData) {
        this.resultType = resultType;
        this.respData.add(respData);
    }

    public void success(T data) {
        this.respData.add(data);
        this.resultType = ResultType.SUCCESS;
    }

    public void success(List<T> data) {
        this.respData.addAll(data);
        this.resultType = ResultType.SUCCESS;
    }

    public ResultType getResultType() {
        return this.resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public void setResultType(ResultType resultType, String errMsg) {
        this.resultType = resultType;
        this.resultType.setDesc(errMsg);
    }

    public List<T> getRespData() {
        return this.respData;
    }

    public void setRespData(List<T> respData) {
        this.respData = respData;
    }
}
