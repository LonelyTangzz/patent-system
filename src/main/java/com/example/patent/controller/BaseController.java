package com.example.patent.controller;

import com.example.patent.entity.basic.BaseResp;
import com.example.patent.entity.basic.ResponseResult;
import com.example.patent.entity.basic.ResultType;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/7.
 * @name: BaseController
 * @description: 基础控制层
 */
@Configuration
@RestController
public class BaseController {
    protected static Logger logger = LoggerFactory.getLogger(BaseController.class);

    public BaseController() {
    }

    public <T> void setResult(int status, String message, List<T> object, HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult(status, message, object);
        this.setResponse(response, result);
    }

    public <T> void setResult(ResultType resultType, List<T> object, HttpServletRequest request, HttpServletResponse response) {
        ResponseResult result = new ResponseResult(resultType.getValue(), resultType.getDesc(), object);
        this.setResponse(response, result);
    }

    private void setResponse(HttpServletResponse response, ResponseResult result) {
        try {
            response.setContentType("application/json;charset=utf-8");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Expires", "0");
            response.setHeader("Pragma", "No-cache");
            response.setStatus(200);
            GsonBuilder gsonBuilder = new GsonBuilder();
            String data = gsonBuilder.create().toJson(result);
            response.getWriter().write(data);
        } catch (Exception var5) {
            var5.printStackTrace();
            logger.error(var5.toString());
        }

    }

    public ResponseResult setResult(BaseResp baseResp) {
        ResponseResult result = new ResponseResult();
        result.setStatus(baseResp.getResultType().getValue());
        result.setMsg(baseResp.getResultType().getDesc());
        result.setData(baseResp.getRespData());
        return result;
    }


}
