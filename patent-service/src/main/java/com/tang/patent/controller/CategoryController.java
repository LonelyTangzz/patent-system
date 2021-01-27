package com.tang.patent.controller;

import com.tang.api.CategoryApi;
import com.tang.basic.BaseController;
import com.tang.basic.ResponseResult;
import com.tang.patent.logger.LoggerUtils;
import com.tang.patent.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author tangzy.
 * @version 1.0
 * @date 2021/1/27.
 * @name: CategoryController
 * @description: 类别API实现类, 控制层
 */
@Slf4j
@RestController
public class CategoryController extends BaseController implements CategoryApi {
    /**
     * 打印日志用
     */
    LoggerUtils logger = new LoggerUtils(this.getClass().getName());

    @Resource
    CategoryService categoryService;

    /**
     * 添加分类接口
     *
     * @param categoryName 分类名
     * @return 操作结果
     */
    @Override
    public ResponseResult addCategory(String categoryName) {
        return null;
    }
}
