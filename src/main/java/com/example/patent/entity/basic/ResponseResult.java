package com.example.patent.entity.basic;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/7.
 * @name: ResponseResult
 * @description: 操作返回类
 */
public class ResponseResult<T> implements Serializable {

    /**
     * 状态 0-success,1-error
     */
    private int status;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 放置返回信息
     */
    private List<T> data;

    public ResponseResult(ResultType resultType, List<T> data) {
        this.status = resultType.getValue();
        this.msg = resultType.getDesc();
        this.setData(data);
    }

    public ResponseResult(ResultType resultType, String errMsg, List<T> data) {
        this.status = resultType.getValue();
        this.msg = errMsg;
        this.setData(data);
    }

    public ResponseResult(int status, String msg, List<T> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public void setData(List<T> data) {
        if (data != null && data.size() == 1 && data.get(0) == null) {
            data = new ArrayList<>();
        }
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getData() {
        return this.data;
    }

    public int getStatus() {
        return this.status;
    }

    public String getMsg() {
        return this.msg;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResponseResult)) {
            return false;
        } else {
            ResponseResult<?> other = (ResponseResult) o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getStatus() != other.getStatus()) {
                return false;
            } else {
                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof ResponseResult;
    }

    @Override
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        result = result * 59 + this.getStatus();
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "ResponseResult(status=" + this.getStatus() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
    }

    public ResponseResult() {
    }
}

